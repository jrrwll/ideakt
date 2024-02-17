package org.dreamcat.ideatk.get_start.stock;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-10
 * @see com.intellij.openapi.components.StoragePathMacros
 * 持久化配置到 options/stock.xml
 */
@Data
@State(name = "StockDataSetting", storages = @Storage("stock.xml"))
public class StockDataState implements PersistentStateComponent<StockDataState> {

    public static StockDataState getInstance() {
        return ApplicationManager.getApplication().getService(StockDataState.class);
    }

    private String token;
    private List<String> gids = new ArrayList<>();

    @Override
    public @Nullable StockDataState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull StockDataState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
