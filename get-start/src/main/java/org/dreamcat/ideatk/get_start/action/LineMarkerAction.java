package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-02
 */
public class LineMarkerAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiFile first = null;
        // 对PsiElement的写操作需要在 writeAction 上下文中
        WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {

        });
    }


}
