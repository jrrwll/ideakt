package org.dreamcat.ideatk.util;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

/**
 * @author Jerry Will
 * @version 2024-01-25
 */
public class ActionUtil {

    public static void registerAction(String actionId, AnAction action) {

        ActionManager actionManager = ActionManager.getInstance();
        actionManager.registerAction(actionId, action);

        String groupId = null;
        DefaultActionGroup group = (DefaultActionGroup) actionManager.getAction(groupId);
        group.add(action);
    }
}
