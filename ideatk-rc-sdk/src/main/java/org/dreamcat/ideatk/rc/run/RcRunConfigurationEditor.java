package org.dreamcat.ideatk.rc.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import javax.swing.JComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcRunConfigurationEditor extends SettingsEditor<RcRunConfiguration> {

    public RcRunConfigurationEditor(@NotNull Project project) {

    }

    @Override
    protected void resetEditorFrom(@NotNull RcRunConfiguration s) {

    }

    @Override
    protected void applyEditorTo(@NotNull RcRunConfiguration s) throws ConfigurationException {

    }

    @Override
    protected @NotNull JComponent createEditor() {
        return null;
    }
}
