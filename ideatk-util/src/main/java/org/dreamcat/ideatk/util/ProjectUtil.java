package org.dreamcat.ideatk.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class ProjectUtil {

    public static List<Module> getModules(Project project) {
        return Arrays.asList(ModuleManager.getInstance(project).getModules());
    }

    public static String getProperty(Project project, String name) {
        PropertiesComponent props = PropertiesComponent.getInstance(project);
        return props.getValue(name);
    }

    public static String getProjectBaseDir(Project project) {
        return ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0].getPath();
    }

}
