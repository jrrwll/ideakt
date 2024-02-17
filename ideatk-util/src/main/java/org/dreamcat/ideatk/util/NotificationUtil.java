package org.dreamcat.ideatk.util;

import com.intellij.ide.BrowserUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.Icon;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;

public class NotificationUtil {

    // NotificationDisplayType.BALLOON
    private static final NotificationGroup BALLOON = null;
    // NotificationDisplayType.STICKY_BALLOON
    private static final NotificationGroup STICKY_BALLOON = null;

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static void notify(String content) {
        notify(content, (Project) null);
    }

    public static void notify(String content, String url) {
        notify(content, url, null);
    }

    public static void notify(String content, Project project) {
        notify(content, null, NotificationType.INFORMATION, project);
    }

    public static void notify(String content, String url, Project project) {
        notify(content, url, NotificationType.INFORMATION, project);
    }

    // ---- ---- ---- ----    ---- ---- ---- ----    ---- ---- ---- ----

    public static void notify(Throwable t) {
        notify(t, (Project) null);
    }

    public static void notify(Throwable t, String url) {
        notify(t, url, null);
    }

    public static void notify(Throwable t, Project project) {
        notify(t, null, project);
    }

    public static void notify(Throwable t, String url, Project project) {
        notifyError(ExceptionUtils.getStackTrace(t), url, project);
    }

    // ---- ---- ---- ----    ---- ---- ---- ----    ---- ---- ---- ----

    public static void notifyError(String content) {
        notifyError(content, (Project) null);
    }

    public static void notifyError(String content, String url) {
        notifyError(content, url, null);
    }

    public static void notifyError(String content, Project project) {
        notifyError(content, null, project);
    }

    public static void notifyError(String content, String url, Project project) {
        notify(content, url, NotificationType.ERROR, project);
    }

    // ---- ---- ---- ----    ---- ---- ---- ----    ---- ---- ---- ----

    public static void notify(String content, String url, NotificationType notificationType, Project project) {
        List<NotificationGroup> groups = new ArrayList<>(
                NotificationGroupManager.getInstance().getRegisteredNotificationGroups());
        NotificationGroup group = groups.stream()
                .filter(it -> it.getDisplayId().startsWith("System"))
                .findFirst().orElse(null);
        if (group == null) {
            group = NotificationGroupManager.getInstance().getNotificationGroup("System messages");
        }

        Notification notification = group.createNotification(content, notificationType);
        if (url != null) {
            notification.addAction(NotificationAction.createSimple(url,
                    () -> BrowserUtil.browse(url, project)));
        }
        notification.notify(project);
        Notifications.Bus.notify(notification);
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static void showRestartIde(String message) {
        builder(message).sticky().button("Restart IDE", e -> {
            ApplicationManager.getApplication().exit(
                    true, false, true);
        }).show();
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static Builder builder(String message) {
        return new Builder(message);
    }

    public static class Builder {

        private NotificationType type = NotificationType.INFORMATION;
        private boolean sticky;
        private String title;
        private String content;
        private boolean important;
        private Icon icon;
        private Project project;
        private final List<BalloonAction> buttons = new ArrayList<>();

        public Builder(String content) {
            this.content = content;
        }

        public Notification show() {
            NotificationGroup notificationGroup = sticky ? STICKY_BALLOON : BALLOON;
            Notification notification = notificationGroup.createNotification(content, type);

            if (content != null) notification.setContent(content);
            if (title != null) notification.setTitle(title);
            if (icon != null) notification.setIcon(icon);
            notification.setImportant(important);

            for (BalloonAction button : buttons) {
                notification.addAction(button);
            }

            notification.notify(project);
            Notifications.Bus.notify(notification);
            return notification;
        }

        public Builder sticky() {
            this.sticky = true;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder icon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public Builder important() {
            this.important = true;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder project(Project project) {
            this.project = project;
            return this;
        }

        public Builder button(String text, Consumer<AnActionEvent> actionPerformer) {
            this.buttons.add(new BalloonAction(text, actionPerformer));
            return this;
        }
    }

    private static class BalloonAction extends AnAction implements DumbAware {

        private final Consumer<AnActionEvent> actionPerformer;

        public BalloonAction(String text, Consumer<AnActionEvent> actionPerformer) {
            super(text);
            this.actionPerformer = actionPerformer;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            actionPerformer.accept(e);
        }
    }
}
