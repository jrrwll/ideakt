package org.dreamcat.ideatk.get_start.task;

import com.intellij.openapi.progress.ProgressIndicator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.dreamcat.common.function.IConsumer;
import org.dreamcat.ideatk.get_start.common.Constants;
import org.dreamcat.ideatk.util.ProgressTaskUtil;
import org.dreamcat.ideatk.util.UpdateSettingsUtil;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
@Setter
@RequiredArgsConstructor
public class UpdatePluginTask {

    private final PluginUpdateInfo pluginUpdateInfo;
    private IConsumer<String, ?> callback;

    public void runBackground() {
        ProgressTaskUtil.runBackground(null,
                Constants.PLUGIN_TITLE + "更新", true,
                this::updatePlugin, callback);
    }

    private void updatePlugin(ProgressIndicator progressIndicator) throws Exception {
        UpdateSettingsUtil.updatePlugin(
                pluginUpdateInfo.getName(), pluginUpdateInfo.getVersion(),
                pluginUpdateInfo.getDownloadUrl(), pluginUpdateInfo.getUpdateLogUrl(),
                Constants.PLUGIN_ID, progressIndicator);
    }
}
