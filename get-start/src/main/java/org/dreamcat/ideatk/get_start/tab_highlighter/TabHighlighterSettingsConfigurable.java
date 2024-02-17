package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.application.options.colors.ColorAndFontDescriptionPanel;
import com.intellij.application.options.colors.TextAttributesDescription;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.messages.MessageBus;
import javax.swing.JComponent;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class TabHighlighterSettingsConfigurable implements SearchableConfigurable  {

    private final ColorAndFontDescriptionPanel panel = new ColorAndFontDescriptionPanel();
    private final HighlighterSettingsConfig config;
    private final EditorColorsScheme editorColorsScheme;
    private final MessageBus bus;

    public TabHighlighterSettingsConfigurable(Project project) {
        this.config = HighlighterSettingsConfig.getSettings(project);
        this.editorColorsScheme = EditorColorsManager.getInstance().getGlobalScheme();
        this.bus = ApplicationManager.getApplication().getMessageBus();
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "preference.HighlighterSettingsConfigurable";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Active Tab Highlighter Plugin";
    }

    @Override
    public @Nullable @NonNls String getHelpTopic() {
        return "preference.HighlighterSettingsConfigurable";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return panel.getPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() {
        this.config.storeBackground(this.panel.isBackgroundSet(), this.panel.getBackground());
        TextAttributesDescription attributesDescription = this.config.getAttributesDescription();
        this.panel.apply(attributesDescription, this.editorColorsScheme);
        this.bus.syncPublisher(HighlighterSettingsChangeListener.CHANGE_HIGHLIGHTER_SETTINGS_TOPIC)
                .settingsChanged();
    }

    @Override
    public void reset() {
        this.panel.reset(this.config.getAttributesDescription());
    }
}
