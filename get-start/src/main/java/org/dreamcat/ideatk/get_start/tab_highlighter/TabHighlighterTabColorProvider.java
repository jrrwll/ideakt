package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.FileColorManager;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class TabHighlighterTabColorProvider implements EditorTabColorProvider {

    @Override
    public @Nullable Color getEditorTabColor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        FileColorManager fileColorManager = FileColorManager.getInstance(project);
        HighlighterSettingsConfig config = HighlighterSettingsConfig.getSettings(project);
        if (config != null && config.isBackgroundEnable()) {
            EditorWindow activeWindow = fileEditorManagerEx.getCurrentWindow();
            if (activeWindow != null) {
                EditorWithProviderComposite selectedEditor = activeWindow.getSelectedEditor();
                if (selectedEditor != null && virtualFile.equals(selectedEditor.getFile())) {
                    return config.getBackgroundColor();
                }
            }
        }
        return fileColorManager.getFileColor(virtualFile);
    }
}
