package org.dreamcat.ideatk.get_start.rc;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
public class ScriptRunConfigurationType extends ConfigurationTypeBase {

    static final String ID = ScriptRunConfiguration.class.getSimpleName();

    private static final ScriptRunConfigurationType INSTANCE = new ScriptRunConfigurationType();

    public static ScriptRunConfigurationType getInstance() {
        return INSTANCE;
    }

    protected ScriptRunConfigurationType() {
        super(ID, "My Script", "invoke a script",
                AllIcons.FileTypes.Text);
    }

    @NotNull
    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{ScriptConfigurationFactory.getInstance()};
    }
}
