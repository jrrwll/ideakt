package org.dreamcat.ideatk.get_start.handler;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class MyProjectListener implements ProjectManagerListener {

    @Override
    public void projectClosed(@NotNull Project project) {
        MyWindowFocusListener.remove(project);
    }
}
