package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import java.awt.Dimension;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class DialogAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        MyDialog dialog = new MyDialog();
        dialog.init(project);
        dialog.setMinimumSize(new Dimension(600, 600));
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }
}
