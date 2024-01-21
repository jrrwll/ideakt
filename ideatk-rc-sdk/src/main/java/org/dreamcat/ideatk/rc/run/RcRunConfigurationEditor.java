package org.dreamcat.ideatk.rc.run;

import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiFile;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import org.dreamcat.ideatk.rc.RcPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcRunConfigurationEditor extends SettingsEditor<RcRunConfiguration> {

    private final Project project;
    private JPanel mainPanel;
    private JPanel requestPanel;
    private TextFieldWithBrowseButton pathField;
    private RcEnvironmentComboBox environmentComboBox;
    private RcRequestComboBox requestNameComboBox;
    private JBLabel envLabel;
    private JBLabel requestLabel;
    private JBRadioButton runFileRadioButton;
    private JBRadioButton runSingleRequestRadioButton;

    public RcRunConfigurationEditor(@NotNull Project project) {
        super();
        this.project = project;
        this.setupUI();

        this.pathField.addBrowseFolderListener((String)null, (String)null, project, FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor());
        this.pathField.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
            protected void textChanged(@NotNull DocumentEvent e) {
                RcEnvironmentComboBox.HttpEnvironmentItem env = RcRunConfigurationEditor.this.environmentComboBox.getSelectedItem();
                String envName = env != null ? env.getName() : null;
                RcRunConfigurationEditor.this.updateState(
                        RcRunConfigurationEditor.this.pathField.getText(),
                        envName,
                        RcRunConfigurationEditor.this.requestNameComboBox.getSelectedRequestIndex(),
                        RcRunConfigurationEditor.this.requestNameComboBox.getSelectedRequestIdentifier(),
                        RcRunConfigurationEditor.this.getSelectedRunType());
            }
        });
        this.envLabel.setLabelFor(this.environmentComboBox);
        this.requestLabel.setLabelFor(this.requestNameComboBox);
        this.runSingleRequestRadioButton.addItemListener(new RunTypeSelectionButtonItemListener(this, HttpRequestRunType.SINGLE_REQUEST));
        this.runFileRadioButton.addItemListener(new RunTypeSelectionButtonItemListener(this, HttpRequestRunType.ALL_IN_FILE));
    }

    //  丢弃通过该UI所做的所有未确认的用户更改
    @Override
    protected void resetEditorFrom(@NotNull RcRunConfiguration configuration) {
        RcRunConfiguration.Settings settings = configuration.getSettings();
        this.pathField.setText(settings.getPath());
        this.updateState(settings.getPath(), settings.getEnvironment(),
                settings.getIndex(), settings.getRequestIdentifier(), settings.getRunType());
    }

    private void updateState(@Nullable String path, @Nullable String env, int index,
            @Nullable String identifier, @Nullable RcRunType runType) {
        PsiFile file = RcRunConfiguration.findFileByPath(this.project, path);
        this.environmentComboBox.reset(this.project, file, env);
        this.setSelectedRunType(runType);
        if (runType != null) {
            this.requestPanel.setVisible(runType == RcRunType.SINGLE_REQUEST);
        }

        if (file instanceof RcPsiFile) {
            this.requestNameComboBox.reset((RcPsiFile)file, index, identifier);
            this.requestNameComboBox.setEnabled(this.requestNameComboBox.getModel().getSize() > 1);
        } else {
            this.requestNameComboBox.setEnabled(false);
        }
    }

    // 确认更改，即将当前 UI 状态复制到目标设置对象中
    @Override
    protected void applyEditorTo(@NotNull RcRunConfiguration configuration) throws ConfigurationException {
        RcRunConfiguration.Settings settings = configuration.getSettings();
        settings.setPath(this.pathField.getText());
        settings.setIndex(this.requestNameComboBox.getSelectedRequestIndex());
        settings.setRunType(this.getSelectedRunType());
        settings.setRequestIdentifier(this.requestNameComboBox.getSelectedRequestIdentifier());
        HttpEnvironmentComboBox.HttpEnvironmentItem env = this.environmentComboBox.getSelectedItem();
        settings.setEnvironment(env == null ? "" : env.getName());
    }

    /**
     * 由 IDE 调用并显示运行配置特定的 UI
     * @see SettingsEditor#getComponent()
     * @return
     */
    @Override
    protected @NotNull JComponent createEditor() {
        return mainPanel;
    }

    private @Nullable RcRunType getSelectedRunType() {
        if (this.runFileRadioButton.isSelected()) {
            return RcRunType.ALL_IN_FILE;
        } else {
            return this.runSingleRequestRadioButton.isSelected() ? RcRunType.SINGLE_REQUEST : null;
        }
    }

    private void setSelectedRunType(@Nullable RcRunType runType) {
        if (runType == RcRunType.ALL_IN_FILE) {
            this.runFileRadioButton.setSelected(true);
        } else if (runType == RcRunType.SINGLE_REQUEST) {
            this.runSingleRequestRadioButton.setSelected(true);
        }
    }

    private void createUIComponents() {
        myMainClass = new LabeledComponent<ComponentWithBrowseButton>();
        myMainClass.setComponent(new TextFieldWithBrowseButton());
    }
}
