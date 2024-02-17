package org.dreamcat.ideatk.get_start.rc;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.FormBuilder;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
public class MySettingsEditor extends SettingsEditor<MyRunConfiguration> {

    private final JPanel panel;
    private final TextFieldWithBrowseButton scriptPathField;

    public MySettingsEditor() {
        this.scriptPathField = new TextFieldWithBrowseButton();
        this.scriptPathField.addBrowseFolderListener("Select Script File", null, null,
                FileChooserDescriptorFactory.createSingleFileDescriptor());

        this.panel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Script file", this.scriptPathField)
                .getPanel();
    }

    @Override
    protected void resetEditorFrom(@NotNull MyRunConfiguration runConfiguration) {
        scriptPathField.setText(runConfiguration.getScriptName());
    }

    @Override
    protected void applyEditorTo(@NotNull MyRunConfiguration runConfiguration) throws ConfigurationException {
        runConfiguration.setScriptName(scriptPathField.getText());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return panel;
    }
}