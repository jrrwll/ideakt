package org.dreamcat.ideatk.get_start.stock;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * 窗体填充面板
 *
 * @author Jerry Will
 * @version 2024-02-10
 */
public class StockToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 窗体
        StockSimpleToolWindowPanel viewPanel = new StockSimpleToolWindowPanel(project);
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(viewPanel, "股票面板", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content, 0);
    }
}
