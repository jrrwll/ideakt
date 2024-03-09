package org.dreamcat.ideatk.get_start.rc;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.StoredProperty;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import java.util.List;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.ideatk.get_start.rc.ScriptRunConfiguration.Options;
import org.dreamcat.ideatk.util.RunConfigurationUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ScriptRunConfiguration extends RunConfigurationBase<Options> {

    protected ScriptRunConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    public List<String> getCommand() {
        return getOptions().getCommand();
    }

    public void setCommand(List<String> command) {
        getOptions().setCommand(command);
    }

    @Override
    protected @NotNull Options getOptions() {
        return (Options) super.getOptions();
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new ScriptSettingsEditor();
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) throws ExecutionException {
        List<String> command = getOptions().getCommand();
        if (ObjectUtil.isEmpty(command)) {
            throw new ExecutionException("Command is null");
        }
        return RunConfigurationUtil.createCommandLineState(
                env, command.toArray(String[]::new));
    }

    public static class Options extends RunConfigurationOptions {

        private final StoredProperty<List<String>> command = (StoredProperty)(list()
                .provideDelegate(this, "command"));

        public List<String> getCommand() {
            return command.getValue(this);
        }

        public void setCommand(List<String> command) {
            this.command.setValue(this, command);
        }
    }
}
