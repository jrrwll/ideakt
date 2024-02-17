package org.dreamcat.ideatk.get_start.stock;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;
import lombok.Getter;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
@Getter
public class StockSimpleToolWindowPanel extends SimpleToolWindowPanel {

    private final StockToolWindowForm form;
    private final Project project;

    public StockSimpleToolWindowPanel(Project project) {
        super(false, true);
        this.form = new StockToolWindowForm();
        this.project = project;

        // 设置窗体侧边栏按钮
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new SettingBar(this));
        group.add(new RefreshBar(this));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("bar", group, false);
        toolbar.setTargetComponent(this);
        setToolbar(toolbar.getComponent());

        // 分割线
        JBSplitter splitter = new JBSplitter(false);
        splitter.setSplitterProportionKey("main.splitter.key");
        splitter.setFirstComponent(form.panel);
        splitter.setProportion(0.3f);
        setContent(splitter);
    }
}
