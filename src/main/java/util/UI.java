package util;

import com.intellij.execution.configurations.JavaParameters;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class UI {

    {
        ComponentManager.getService(null);
        Registry.is("a.b.c");

        Module module = null;
        VirtualFile moduleDir = ProjectUtil.guessModuleDir(module);
        moduleDir.getPath();

        Project project = null;
        ModuleManager.getInstance(project).getModules();

        JavaParameters javaParameters= new JavaParameters();
        javaParameters.getVMParametersList().add("-Xms256");
        javaParameters.getClassPath().add(new File("/lib/jna.jar"));
    }

    String content;
    NotificationType notificationType;


}
