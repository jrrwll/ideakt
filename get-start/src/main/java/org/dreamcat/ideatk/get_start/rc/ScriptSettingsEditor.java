package org.dreamcat.ideatk.get_start.rc;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.FormBuilder;
import java.util.Collections;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-09
 */
public class ScriptSettingsEditor extends SettingsEditor<ScriptRunConfiguration> {

    private final JPanel panel;
    private final TextFieldWithBrowseButton scriptPathField;

    public ScriptSettingsEditor() {
        this.scriptPathField = new TextFieldWithBrowseButton();
        this.scriptPathField.addBrowseFolderListener("Select Script File", null, null,
                FileChooserDescriptorFactory.createSingleFileDescriptor());

        this.panel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Script file", this.scriptPathField)
                .getPanel();
    }

    @Override
    protected void resetEditorFrom(@NotNull ScriptRunConfiguration runConfiguration) {
        scriptPathField.setText(String.join(" ", runConfiguration.getCommand()));
    }

    @Override
    protected void applyEditorTo(@NotNull ScriptRunConfiguration runConfiguration) throws ConfigurationException {
        runConfiguration.setCommand(Collections.singletonList(scriptPathField.getText()));
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return panel;
    }
}