package org.dreamcat.ideatk.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class MessagesUtil {

    public static boolean confirm(String message, String title, Project project) {
        return InvokeUtil.invokeAndWait(() -> Messages.showYesNoDialog(
                project, message, title, "确认", "取消",
                Messages.getQuestionIcon()) == Messages.YES);
    }

    public static void success(String message) {
        InvokeUtil.invokeAndWait(() -> {
            Messages.showInfoMessage(message, "提示");
            return null;
        });
    }

    public static void warn(String message) {
        InvokeUtil.invokeAndWait(() -> {
            Messages.showWarningDialog(message, "提示");
            return null;
        });
    }

    public static void error(String message) {
        InvokeUtil.invokeAndWait(() -> {
            Messages.showErrorDialog(message, "提示");
            return null;
        });
    }

    public static String prompt(String message, String initialValue) {
        return InvokeUtil.invokeAndWait(() -> Messages.showInputDialog(
                message, "请输入", null, initialValue, null));
    }
}
