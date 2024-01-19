package org.dreamcat.ideatk.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class NotificationUtil {

    Notification notification;

    String content;
    NotificationType notificationType;
    Project project;

    public static Notification show(Project project) {
        NotificationGroup group = NotificationGroup.create(
                "", NotificationDisplayType.NONE,
                false, "", "", null);
        Notification notification = new Notification(group.getDisplayId(),
                "",
                NotificationType.WARNING);
        notification.action

        if (notification != null && !notification.isExpired()) {
            notification.expire();
        }
        notification.notify(project);
        return notification;
    }
}
