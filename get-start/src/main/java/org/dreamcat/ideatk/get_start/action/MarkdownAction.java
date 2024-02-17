package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.io.File;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-02
 */
public class MarkdownAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        File file = getSelectedMarkdownFile(e);
        if (file == null) {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        File file = getSelectedMarkdownFile(e);
        if (file == null) {
            NotificationUtil.notify("not a markdown file");
            return;
        }

        NotificationUtil.notify(file.getPath());
    }

    private File getSelectedMarkdownFile(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return null;
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) return null;

        PsiFile psiFile = EditorPsiUtil.getFile(e);
        if (psiFile == null || !psiFile.isPhysical()) return null;

        VirtualFile vf = psiFile.getVirtualFile();
        if (!vf.isInLocalFileSystem()) return null;

        String path = vf.getCanonicalPath();
        if (path == null) return null;
        File file = new File(path);
        if (!file.exists() || !file.isFile() || !path.endsWith(".md")) return null;
        return file;
    }
}
