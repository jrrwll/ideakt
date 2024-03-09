package org.dreamcat.ideatk.get_start.rc;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
public class ScriptConfigurationFactory extends ConfigurationFactory {

    private static final ScriptConfigurationFactory INSTANCE =
            new ScriptConfigurationFactory(ScriptRunConfigurationType.getInstance());


    public static ScriptConfigurationFactory getInstance() {
        return INSTANCE;
    }

    public ScriptConfigurationFactory(ScriptRunConfigurationType configurationType) {
        super(configurationType);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return ScriptRunConfigurationType.ID;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new ScriptRunConfiguration(project, this, "Mrpc");
    }

    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return ScriptRunConfiguration.Options.class;
    }

}
