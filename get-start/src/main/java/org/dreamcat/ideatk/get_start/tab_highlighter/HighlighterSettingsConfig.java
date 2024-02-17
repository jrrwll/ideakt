package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.application.options.colors.TextAttributesDescription;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import java.awt.Color;
import java.util.Objects;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
@State(
        name = "ActiveTabHighlighterConfiguration",
        storages = {@Storage("active-tab-highlighter.xml")}
)
public class HighlighterSettingsConfig implements PersistentStateComponent<HighlighterSettingsConfig.PersistentState> {

    private final PersistentState persistentState;
    private final TextAttributesDescription attributesDescription;

    public static @Nullable HighlighterSettingsConfig getSettings(Project project) {
        return project.getService(HighlighterSettingsConfig.class);
    }

    public HighlighterSettingsConfig() {
        this.persistentState = new PersistentState();
        this.persistentState.background.store(true, new Color(173, 46, 156));

        TextAttributes attributes = new TextAttributes();
        attributes.setBackgroundColor(this.persistentState.background.getColor());
        TextAttributesKey textAttributesKey = TextAttributesKey.createTextAttributesKey("HIGHLIGHTER_TAB");
        this.attributesDescription = new TextAttributesDescription(
                "Highlighter", "Highlighter",
                attributes, textAttributesKey,
                EditorColorsManager.getInstance().getGlobalScheme(), null, null) {
        };
    }

    @Override
    public @Nullable PersistentState getState() {
        return persistentState;
    }

    @Override
    public void loadState(@NotNull PersistentState highlighterSettingsConfig) {
        XmlSerializerUtil.copyBean(persistentState, this.persistentState);
        this.updateAttributes(persistentState);
    }

    private void updateAttributes(PersistentState state) {
        this.attributesDescription.setBackgroundColor(state.background.getColor());
        this.attributesDescription.setBackgroundChecked(state.background.isEnabled());
    }

    public TextAttributesDescription getAttributesDescription() {
        return attributesDescription;
    }

    public Color getBackgroundColor() {
        return this.persistentState.background.getColor();
    }

    public boolean isBackgroundEnable() {
        return persistentState.background.enabled;
    }

    public void storeBackground(boolean enabled, Color color) {
        this.persistentState.background.store(enabled, color);
    }

    @Data
    public static class PersistentState {

        public PersistentColor background = new PersistentColor();
        public PersistentColor foreground = new PersistentColor();
    }

    @Data
    public static class PersistentColor {

        public boolean enabled = false;
        public Integer red;
        public Integer green;
        public Integer blue;

        public Color getColor() {
            return this.enabled ? new Color(this.red, this.green, this.blue) : null;
        }

        public void store(boolean enabled, Color color) {
            this.enabled = enabled;
            if (!enabled) return;
            Objects.requireNonNull(color, "Color cannot be null when enabled");
            this.red = color.getRed();
            this.green = color.getGreen();
            this.blue = color.getBlue();
        }

        public boolean isDifferentColorThan(Color color) {
            return !Objects.equals(red, color.getRed()) ||
                    !Objects.equals(green, color.getGreen()) ||
                    !Objects.equals(blue, color.getBlue());
        }
    }
}
