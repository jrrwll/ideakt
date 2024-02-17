package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil.JavaSelectInfo;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-25
 */
public class JavaSelectAction extends AnAction {

    // 更新Action状态(state)的时候会调用AnAction.update() 方法
    // AnAction.update() 方法将在 UI 线程上频繁调用
    @Override
    public void update(@NotNull AnActionEvent e) {
        JavaSelectInfo selectInfo = EditorPsiUtil.getJavaSelectInfo(e);
        if (selectInfo == null || selectInfo.getClazz() == null) {
            // 不显示该Action
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    // 当点击按钮或者toolbar的时候，会回调 AnAction 的actionPerformed方法
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        NotificationUtil.notify("JAVA_VENDOR=" + SystemInfo.JAVA_VENDOR);
        NotificationUtil.notifyError("JAVA_RUNTIME_VERSION=" + SystemInfo.JAVA_RUNTIME_VERSION);

        JavaSelectInfo selectInfo = EditorPsiUtil.getJavaSelectInfo(e);
        if (selectInfo == null) return;
        PsiClass clazz = selectInfo.getClazz();
        if (clazz == null) return;

        String className = clazz.getQualifiedName();
        NotificationUtil.notify(className);

        VirtualFile vf = clazz.getContainingFile().getVirtualFile();
        NotificationUtil.notify("Jump to " + vf.getPath(), "https://github.com");
    }
}
