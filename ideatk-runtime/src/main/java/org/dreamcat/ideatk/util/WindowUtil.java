package org.dreamcat.ideatk.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.jcef.JBCefBrowser;
import java.util.Collections;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class WindowUtil {

    public static ToolWindow getToolWindow(Project project, String toolWindowId) {
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(toolWindowId);

        toolWindow.setContentUiType(ToolWindowContentUiType.TABBED, null);
        toolWindow.setTitleActions(Collections.singletonList(
                DumbAwareAction.create("", e -> {})));

        ContentManager contentManager = toolWindow.getContentManager();
        JBCefBrowser browser = new JBCefBrowser();
        Content content = contentManager.getFactory().createContent(
                browser.getComponent(), "", false);
        content.putUserData(Key.create("id"), "url");
        content.setCloseable(false);
        ApplicationManager.getApplication().invokeLater(() -> {
            contentManager.addContent(content);
        });
        return toolWindow;
    }




}
