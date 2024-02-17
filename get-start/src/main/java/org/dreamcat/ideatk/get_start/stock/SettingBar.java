package org.dreamcat.ideatk.get_start.stock;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
public class SettingBar extends DumbAwareAction {

    private final StockSimpleToolWindowPanel panel;

    public SettingBar(StockSimpleToolWindowPanel panel) {
        super("配置面板", "Click to setting", AllIcons.Ide.ConfigFile);
        this.panel = panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        ShowSettingsUtil.getInstance().editConfigurable(panel.getProject(), new StockConfigForm(panel.getForm()));
    }
}
