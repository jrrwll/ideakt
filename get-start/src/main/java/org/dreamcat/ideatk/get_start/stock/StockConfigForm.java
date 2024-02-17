package org.dreamcat.ideatk.get_start.stock;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-10
 */
public class StockConfigForm implements Configurable {

    private final StockToolWindowForm windowForm;

    public JPanel panel;
    public JTextField textField;

    public StockConfigForm(StockToolWindowForm windowForm) {
        this.windowForm = windowForm;
    }

    @Override
    public String getDisplayName() {
        return "自选股配置";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        List<String> gidList = StockDataState.getInstance().getGids();
        gidList.clear();
        String[] gids = textField.getText().trim().split(",");
        for (String gid : gids) {
            gidList.add(gid.trim());
        }
        // 刷新数据
        windowForm.addRows(gidList);
    }
}
