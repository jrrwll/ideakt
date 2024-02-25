package org.dreamcat.ideatk.get_start.action;

import com.intellij.json.psi.JsonFile;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.psi.PsiFile;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil;
import org.dreamcat.ideatk.util.psi.PsiUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-02
 */
public class JsonAction extends DumbAwareAction {

    public static final String ID = JsonAction.class.getName();

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(isEnabled(e));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiFile psiFile = EditorPsiUtil.getFile(e);
        if (psiFile == null) return;
        String path = PsiUtil.getFilePath(psiFile);
        if (path == null) return;

        NotificationUtil.notify(path);
    }

    private static boolean isEnabled(@NotNull AnActionEvent e) {
        if (e.getProject() == null) return false;
        PsiFile psiFile = EditorPsiUtil.getFile(e);
        return psiFile instanceof JsonFile;
    }
}
