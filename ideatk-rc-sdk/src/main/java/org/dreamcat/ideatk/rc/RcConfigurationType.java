package org.dreamcat.ideatk.rc;

import static org.dreamcat.ideatk.rc.RcConstants.RC_DESCRIPTION;
import static org.dreamcat.ideatk.rc.RcConstants.RC_NAME;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import javax.swing.Icon;
import org.dreamcat.ideatk.rc.run.RcRunConfigurationFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nls.Capitalization;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-18
 */
public class RcConfigurationType implements ConfigurationType {

    private final ConfigurationFactory factory = new RcRunConfigurationFactory(this);

    @Override
    public @NotNull @Nls(capitalization = Capitalization.Title) String getDisplayName() {
        return RC_NAME;
    }

    @Override
    public @Nls(capitalization = Capitalization.Sentence) String getConfigurationTypeDescription() {
        return RC_DESCRIPTION;
    }

    @Override
    public Icon getIcon() {
        return RcIcons.run_configuration();
    }

    @Override
    public @NotNull @NonNls String getId() {
        return RC_NAME + ".RunConfigurationType";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{factory};
    }
}
