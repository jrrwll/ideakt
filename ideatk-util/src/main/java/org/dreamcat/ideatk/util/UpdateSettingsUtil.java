package org.dreamcat.ideatk.util;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.plugins.InstalledPluginsState;
import com.intellij.ide.plugins.PluginNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.updateSettings.impl.PluginDownloader;
import java.net.URL;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class UpdateSettingsUtil {

    public static void updatePlugin(
            String name, String version, String downloadUrl, String updateLogUrl,
            PluginId pluginId, ProgressIndicator progressIndicator) throws Exception {
        InstalledPluginsState state = InstalledPluginsState.getInstanceIfLoaded();
        if (state != null && state.wasUpdated(pluginId)) {
            return;
        }

        PluginNode pluginNode = new PluginNode(pluginId);
        pluginNode.setName(name);
        pluginNode.setVersion(version);
        pluginNode.setDownloadUrl(downloadUrl);
        PluginDownloader downloader = PluginDownloader.createDownloader(
                pluginNode, (new URL(downloadUrl)).getHost(), null);
        if (downloader.prepareToInstall(progressIndicator)) {
            downloader.install();
            NotificationUtil.Builder builder = NotificationUtil.builder(name + "已更新至" + version + "，重启IDE即可生效")
                    .sticky().button("立即重启IDE", e -> {
                        ApplicationManager.getApplication().exit(
                                true, false, true);
                    });
            if (updateLogUrl != null) {
                builder.button("查看更新日志", e -> {
                    BrowserUtil.browse( updateLogUrl);
                });
            }
            builder.show();
        }
    }
}
