package org.dreamcat.ideatk.rc;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.dreamcat.ideatk.rc.debug.DebugRcProfileState;
import org.dreamcat.ideatk.rc.run.RunRcProfileState;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.execution.configurations.RuntimeConfigurationException;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcRunConfiguration extends LocatableConfigurationBase<Element> {

    private static final Logger log = Logger.getInstance(RcRunConfiguration.class);

    protected RcRunConfiguration(@NotNull Project project,
            @NotNull ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RcRunConfigurationEditor(this.getProject());
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment)
            throws ExecutionException {
        return this.getState(executor);
    }

    public @NotNull RunProfileState getState(@NotNull Executor executor) throws ExecutionException {
        Project project = this.getProject();

        try {
            RcExecutionConfig config = this.createConfig(project);
            RcEnvironment environment = this.getEnvironment(project, config.getContext());
            RcVariableSubstitutor substitutor = RcVariableSubstitutor.create(project, environment);
            SMTRunnerConsoleProperties properties = new SMTRunnerConsoleProperties(project, this, "HTTP Client", executor);
            if (executor.getId().equals("Debug")) {
                String name = config.getName(substitutor);
                log.info("Debugging " + name);
                return new DebugRcProfileState(this.getProject(), this.getSettings(), config, properties, substitutor);
            } else {
                String name = config.getName(substitutor);
                log.info("Executing " + name);
                return new RunRcProfileState(this.getProject(), this.getSettings(), config, properties, substitutor);
            }
        } catch (RuntimeConfigurationException e) {
            throw new ExecutionException(e.getMessage());
        }
    }
}
