package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.psi.PsiFile;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.dreamcat.ideatk.util.editor.EditorUtil;
import org.dreamcat.ideatk.util.psi.PsiUtil;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-25
 */
public class MarkdownAction extends AnAction {

    public static final String ID = MarkdownAction.class.getName();

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    // 更新Action状态(state)的时候会调用AnAction.update() 方法
    // AnAction.update() 方法将在 UI 线程上频繁调用
    @Override
    public void update(@NotNull AnActionEvent e) {
        if (!isEnabled(e)) {
            // 不显示该Action
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    // 当点击按钮或者toolbar的时候，会回调 AnAction 的actionPerformed方法
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiFile psiFile = EditorUtil.getFile(e);
        if (psiFile == null) return;
        String path = PsiUtil.getFilePath(psiFile);
        if (path == null) return;

        NotificationUtil.notify("JAVA_VENDOR=" + SystemInfo.JAVA_VENDOR, "https://github.com");
        NotificationUtil.notifyError("JAVA_RUNTIME_VERSION=" + SystemInfo.JAVA_RUNTIME_VERSION);
        NotificationUtil.notify("path=" + path);
    }

    private static boolean isEnabled(@NotNull AnActionEvent e) {
        if (e.getProject() == null) return false;
        PsiFile psiFile = EditorUtil.getFile(e);
        return psiFile instanceof MarkdownFile;
    }
}
