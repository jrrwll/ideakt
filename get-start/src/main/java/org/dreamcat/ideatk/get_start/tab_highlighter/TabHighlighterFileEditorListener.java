package org.dreamcat.ideatk.get_start.tab_highlighter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.FileColorManager;
import com.intellij.ui.tabs.TabInfo;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.SwingUtilities;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class TabHighlighterFileEditorListener implements FileEditorManagerListener, HighlighterSettingsChangeListener {

    private static final Logger log = Logger.getInstance(TabHighlighterFileEditorListener.class);

    private final Project project;
    private final HighlighterSettingsConfig settingsConfig;

    public TabHighlighterFileEditorListener(Project project) {
        this.project = project;
        this.settingsConfig = HighlighterSettingsConfig.getSettings(project);
        this.initialize();
    }

    private void initialize() {
        FileEditorManager fileEditorManager = FileEditorManager.getInstance(this.project);
        FileEditor selectedEditor = fileEditorManager.getSelectedEditor();
        if (selectedEditor != null) {
            VirtualFile file = selectedEditor.getFile();
            SwingUtilities.invokeLater(() -> {
                this.handleSelectionChange(null, file);
            });
        }
    }

    @Override
    public void settingsChanged() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project proj : projects) {
            FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(proj);
            if (manager.getWindows() != null) {
                for (EditorWindow editorWindow : manager.getWindows()) {
                    TabInfo selected = editorWindow.getTabbedPane().getTabs().getSelectedInfo();
                    if (selected == null) continue;
                    selected.setTabColor(this.settingsConfig.getBackgroundColor());
                }
            }
        }
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        log.info(String.format("fileOpen %s", file.getUrl()));
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        log.info(String.format("fileClosed %s", file.getUrl()));
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        if (event.getManager().getProject().equals(this.project)) {
            this.handleSelectionChange(event.getOldFile(), event.getNewFile());
        }
    }

    private void handleSelectionChange(VirtualFile oldFile, VirtualFile newFile) {
        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(this.project);
        FileColorManager fileColorManager = FileColorManager.getInstance(this.project);
        for (EditorWindow editorWindow : manager.getWindows()) {
            this.unhighlight(fileColorManager, oldFile, editorWindow);
            this.highlight(newFile, editorWindow);
        }
    }

    private void highlight(VirtualFile file, EditorWindow editorWindow) {
        if (file == null || editorWindow.findFileComposite(file) == null) return;
        this.setTabColor(this.settingsConfig.getBackgroundColor(), file, editorWindow);
    }

    private void unhighlight(@NotNull FileColorManager fileColorManager, VirtualFile file, EditorWindow editorWindow) {
        if (file == null || editorWindow.findFileComposite(file) == null) return;
        this.setTabColor(fileColorManager.getFileColor(file), file, editorWindow);
    }

    private void setTabColor(Color color, @NotNull VirtualFile file, @NotNull EditorWindow editorWindow) {
        EditorWithProviderComposite fileComposite = editorWindow.findFileComposite(file);
        int index = Arrays.asList(editorWindow.getEditors()).indexOf(fileComposite);
        if (index >= 0 && editorWindow.getTabbedPane() != null) {
            editorWindow.getTabbedPane().getTabs().getTabAt(index).setTabColor(color);
        }
    }
}
