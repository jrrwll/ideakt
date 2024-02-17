package org.dreamcat.ideatk.get_start.stock;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import javax.swing.JComponent;
import org.dreamcat.common.util.ObjectUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-11
 */
public class StockSettingsConfigurable implements SearchableConfigurable {

    private final StockSettingsForm form;

    public StockSettingsConfigurable(){
        this.form = new StockSettingsForm();

        String token = StockDataState.getInstance().getToken();
        if (ObjectUtil.isNotEmpty(token)) {
            form.textField.setText(token);
        }
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "preference.StockSettingsConfigurable";
    }

    @Override
    public String getDisplayName() {
        return "Stock Plugin";
    }

    public @Nullable String getHelpTopic() {
        return "preference.StockSettingsConfigurable";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return form.panel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String token = form.textField.getText();
        StockDataState.getInstance().setToken(token);
        // PasswordSafeUtil.storePassword("stock_password", "myAccount", token);
    }
}
