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
public class MyConfigurationFactory extends ConfigurationFactory {

    private static final MyConfigurationFactory INSTANCE =
            new MyConfigurationFactory(MyRunConfigurationType.getInstance());


    public static MyConfigurationFactory getInstance() {
        return INSTANCE;
    }

    public MyConfigurationFactory(MyRunConfigurationType configurationType) {
        super(configurationType);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return MyRunConfigurationType.ID;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new MyRunConfiguration(project, this, "Mrpc");
    }

    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return MyRunConfiguration.Options.class;
    }

}
