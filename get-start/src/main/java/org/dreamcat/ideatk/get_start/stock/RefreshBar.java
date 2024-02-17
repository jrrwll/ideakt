package org.dreamcat.ideatk.get_start.stock;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
public class RefreshBar extends DumbAwareAction {

    private final StockSimpleToolWindowPanel panel;

    public RefreshBar(StockSimpleToolWindowPanel panel) {
        super("刷新面板", "Click to refresh", AllIcons.Actions.Refresh);
        this.panel = panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        panel.getForm().addRows(StockDataState.getInstance().getGids());
    }
}
