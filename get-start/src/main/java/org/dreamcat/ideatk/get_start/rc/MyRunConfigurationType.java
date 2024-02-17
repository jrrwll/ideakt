package org.dreamcat.ideatk.get_start.rc;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
public class MyRunConfigurationType extends ConfigurationTypeBase {

    static final String ID = MyRunConfiguration.class.getSimpleName();

    private static final MyRunConfigurationType INSTANCE = new MyRunConfigurationType();

    public static MyRunConfigurationType getInstance() {
        return INSTANCE;
    }

    protected MyRunConfigurationType() {
        super(ID, "My Script", "invoke a script",
                AllIcons.FileTypes.Text);
    }

    @NotNull
    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{MyConfigurationFactory.getInstance()};
    }
}
