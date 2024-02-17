package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.ideatk.get_start.stock.StockDataState;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class LoginAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        String token = StockDataState.getInstance().getToken();
        if (ObjectUtil.isEmpty(token)) {
            e.getPresentation().setText("请登入");
        } else {
            e.getPresentation().setText("你好 " + token);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StockDataState.getInstance().setToken(System.getenv("USER"));
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}
