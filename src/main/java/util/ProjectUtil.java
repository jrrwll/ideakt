package util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.dreamcat.common.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class ProjectUtil {

    private static final ThreadLocal<Project> currentProject = new ThreadLocal<>();
    private static volatile Project recentlyFocusedProject = null;

    public static @Nullable Project getProject() {
        return currentProject.get();
    }

    public static void setProject(Project project) {
        currentProject.set(project);
    }

    public static void clearProject() {
        currentProject.remove();
    }

    public static @Nullable Project getRecentlyFocusedProject() {
        return recentlyFocusedProject;
    }

    public static void setRecentlyFocusedProject(Project project) {
        recentlyFocusedProject = project;
    }

    public static @NotNull Project guessProject() {
        Project project = getProject();
        if (project != null) return project;
        project = getRecentlyFocusedProject();
        if (project != null) return project;

        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        return ArrayUtil.getLastOrElse(openProjects,
                ProjectManager.getInstance().getDefaultProject());
    }

    private void test(Project project) {
        project.getLocationHash();
    }
}
