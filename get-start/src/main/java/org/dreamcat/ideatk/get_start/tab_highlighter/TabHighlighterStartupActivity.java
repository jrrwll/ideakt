package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class TabHighlighterStartupActivity implements ProjectActivity {

    private static final Logger log = Logger.getInstance(TabHighlighterStartupActivity.class);

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        log.debug("execute TabHighlighterStartupActivity");
        MessageBus bus = ApplicationManager.getApplication().getMessageBus();
        MessageBusConnection connection = bus.connect();
        TabHighlighterFileEditorListener tabHighlighterFileEditorListener = new TabHighlighterFileEditorListener(project);
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, tabHighlighterFileEditorListener);
        connection.subscribe(HighlighterSettingsChangeListener.CHANGE_HIGHLIGHTER_SETTINGS_TOPIC, tabHighlighterFileEditorListener);
        return null;
    }
}
