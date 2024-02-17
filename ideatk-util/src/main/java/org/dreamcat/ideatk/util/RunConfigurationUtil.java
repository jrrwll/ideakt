package org.dreamcat.ideatk.util;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.PtyCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class RunConfigurationUtil {

    public static <T extends RunConfiguration> T getRunConfiguration(Project project, Class<T> clazz) {
        RunnerAndConfigurationSettings settings = RunManager.getInstance(project)
                .getSelectedConfiguration();
        if (settings == null) return null;
        RunConfiguration runConfiguration = settings.getConfiguration();
        if (!clazz.isInstance(runConfiguration)) return null;
        return clazz.cast(runConfiguration);
    }

    public static <T extends RunConfiguration> List<T> getAllRunConfigurations(Project project, Class<T> clazz) {
        return RunManager.getInstance(project).getAllConfigurationsList()
                .stream().filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T extends RunConfiguration> void execute(Project project,
            @NotNull String configurationName, @NotNull ConfigurationFactory factory,
            Consumer<T> configurator) {
        RunnerAndConfigurationSettings settings = RunManager.getInstance(project)
                .getConfigurationSettingsList(factory.getType()).stream()
                .filter(it -> it.getName().equals(configurationName))
                .findFirst().orElse(null);

        if (settings == null) {
            settings = RunManager.getInstance(project).createConfiguration(
                    configurationName, factory);
        }
        if (configurator != null) configurator.accept((T)settings.getConfiguration());

        Executor executor = DefaultRunExecutor.getRunExecutorInstance();
        ProgramRunnerUtil.executeConfiguration(settings, executor);
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static RunProfileState createPtyCommandLineState(
            ExecutionEnvironment env, String... cmds) {
        return createPtyCommandLineState(env, true, cmds);
    }

    public static RunProfileState createPtyCommandLineState(
            ExecutionEnvironment env, boolean colored, String... cmds) {
        return createCommandLineState(env, colored, true, cmds);
    }

    public static RunProfileState createCommandLineState(
            ExecutionEnvironment env, String... cmds) {
        return createCommandLineState(env, true, cmds);
    }

    public static RunProfileState createCommandLineState(
            ExecutionEnvironment env, boolean colored, String... cmds) {
        return createCommandLineState(env, colored, false, cmds);
    }

    public static RunProfileState createCommandLineState(
            ExecutionEnvironment env, boolean colored, boolean pty, String... cmds) {
        return new CommandLineState(env) {

            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                GeneralCommandLine cli;
                if (pty) {
                    cli = new PtyCommandLine(Arrays.asList(cmds));
                } else {
                    cli = new GeneralCommandLine(cmds);
                }

                ProcessHandlerFactory factory = ProcessHandlerFactory.getInstance();
                OSProcessHandler processHandler;
                if (colored) {
                    processHandler = factory.createColoredProcessHandler(cli);
                } else {
                    processHandler = factory.createProcessHandler(cli);
                }
                ProcessTerminatedListener.attach(processHandler);
                return processHandler;
            }
        };
    }
}
