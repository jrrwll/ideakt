package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.util.messages.Topic;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public interface HighlighterSettingsChangeListener {

    Topic<HighlighterSettingsChangeListener> CHANGE_HIGHLIGHTER_SETTINGS_TOPIC = Topic.create(
            "Highlighter Topic", HighlighterSettingsChangeListener.class);

    void settingsChanged();
}
