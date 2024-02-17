package org.dreamcat.ideatk.get_start.action;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.project.DumbAware;
import javax.swing.Icon;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class AboutActionGroup extends DefaultActionGroup implements DumbAware {

    public AboutActionGroup(){
        super(new LinkAction("changeLogs", AllIcons.General.BalloonInformation,
                        "https://plugins.jetbrains.com/docs/intellij/"),
                new LinkAction("插件介绍", AllIcons.Nodes.LogFolder,
                        "https://github.com"),
                Separator.create(),
                new UpdateAction());
        this.setPopup(true);
    }

    public static class LinkAction extends AnAction implements DumbAware {

        private final String url;

        public LinkAction(String text, Icon icon, String url){
            super(text);
            this.url = url;
            getTemplatePresentation().setIcon(icon);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
            BrowserUtil.browse(url);
        }
    }

    public static class UpdateAction extends AnAction implements DumbAware {

        public UpdateAction(){
            super("检查更新");
            getTemplatePresentation().setIcon(AllIcons.Actions.BuildLoadChanges);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
            // updateCheckTask.run();
            NotificationUtil.notify("无需更新");
        }
    }
}
