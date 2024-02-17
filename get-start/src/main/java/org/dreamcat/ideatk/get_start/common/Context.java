package org.dreamcat.ideatk.get_start.common;

import com.intellij.openapi.project.Project;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class Context {

    // 最近聚焦 project
    private static volatile Project recentlyFocusedProject;

    public static Project getRecentlyFocusedProject() {
        return recentlyFocusedProject;
    }

    public static void setRecentlyFocusedProject(Project project) {
        recentlyFocusedProject = project;
    }
}
