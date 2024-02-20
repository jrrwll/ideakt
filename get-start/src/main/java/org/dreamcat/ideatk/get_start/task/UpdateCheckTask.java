package org.dreamcat.ideatk.get_start.task;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.plugins.InstalledPluginsState;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import lombok.RequiredArgsConstructor;
import org.dreamcat.common.util.RandomUtil;
import org.dreamcat.common.util.StringUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.dreamcat.ideatk.get_start.common.Constants;
import org.dreamcat.ideatk.util.MessagesUtil;
import org.dreamcat.ideatk.util.NotificationUtil;
import org.dreamcat.ideatk.util.ProgressTaskUtil;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
@RequiredArgsConstructor
public class UpdateCheckTask {

    private static final Logger log = Logger.getInstance(UpdateCheckTask.class);

    private final boolean silent;

    public UpdateCheckTask() {
        this(true);
    }

    public void runBackground() {
        ProgressTaskUtil.runBackground(null, "检查更新", true,
                this::checkUpdate, null);
    }

    private void checkUpdate(ProgressIndicator progressIndicator) throws Exception {
        Set<String> errorMessages = new HashSet<>();
        PluginUpdateInfo pluginUpdateInfo = null;
        List<ComponentUpdateInfo> componentUpdateInfos = null;

        try {
            progressIndicator.setText("正在检查插件版本...");
            pluginUpdateInfo = this.checkPlugin();
        } catch (Exception e) {
            log.warn("插件内核版本检查失败", e);
            errorMessages.add(e.getMessage());
        }

        try {
            progressIndicator.setFraction(0.3);
            progressIndicator.setText("正在检查组件版本...");
            componentUpdateInfos = checkComponents();
        } catch (Exception var5) {
            log.warn("组件版本检查失败", var5);
            errorMessages.add(var5.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            if (!this.silent) {
                NotificationUtil.notifyError(Constants.PLUGIN_TITLE + "版本检查失败：" + String.join("\n", errorMessages));
            }
            return;
        }

        if (pluginUpdateInfo == null && componentUpdateInfos.isEmpty() && !this.silent) {
            NotificationUtil.notify("您当前的插件已经是最新版本");
        }
    }

    public PluginUpdateInfo checkPlugin() {
        log.info("开始检查" + Constants.PLUGIN_TITLE + "版本...");
        InstalledPluginsState state = InstalledPluginsState.getInstanceIfLoaded();
        PluginUpdateInfo pluginUpdateInfo;
        if (state != null && state.wasUpdated(Constants.PLUGIN_ID)) {
            NotificationUtil.showRestartIde("上次更新后未重启，重启IDE即可生效");
            pluginUpdateInfo = new PluginUpdateInfo();
            pluginUpdateInfo.setUpdatedWithoutRestart(true);
            return pluginUpdateInfo;
        }

        pluginUpdateInfo = getPluginUpdateInfo();
        if (pluginUpdateInfo == null) {
            log.info(Constants.PLUGIN_TITLE + "无需更新");
            return pluginUpdateInfo;
        }

        log.info(StringUtil.format( "{}需要更新，新版本为：{}，本地版本为：{}",
                Constants.PLUGIN_TITLE, pluginUpdateInfo.getVersion(), Constants.PLUGIN_VERSION));
        UpdatePluginTask updatePluginTask = new UpdatePluginTask(pluginUpdateInfo);
        if (pluginUpdateInfo.isForce()) {
            ApplicationManager.getApplication().invokeLater(updatePluginTask::runBackground);
        } else {
            String content = Constants.PLUGIN_TITLE + "有新的版本更新：" + pluginUpdateInfo.getVersion();
            NotificationUtil.builder(content).button("立即更新", event -> {
                updatePluginTask.setCallback((errorMsg) -> {
                    if (errorMsg != null) {
                        MessagesUtil.error(Constants.PLUGIN_TITLE + "更新失败：" + errorMsg);
                    }
                });
                updatePluginTask.runBackground();
            }).button("查看更新日志", event -> {
                BrowserUtil.browse(pluginUpdateInfo.getUpdateLogUrl());
            }).sticky().show();
        }
        return pluginUpdateInfo;
    }

    public static List<ComponentUpdateInfo> checkComponents() {
        log.info("开始检查组件版本...");
        // Map<String, LocalComponent> installedComponents = Container.getInstalledComponents();
        // if (ObjectUtil.isEmpty(installedComponents)) {
        //     return Collections.emptyList();
        // }

        List<ComponentUpdateInfo> componentUpdateInfos = getComponentUpdateInfo();
        for (ComponentUpdateInfo componentUpdateInfo : componentUpdateInfos) {
            // 只处理已安装且已启用的组件
            // LocalComponent localComponent = installedComponents.get(componentUpdateInfo.getId());
            // if (localComponent == null || localComponent.isDisable()) continue;

            log.info(StringUtil.format("组件{}存在可更新版本, {} -> {}",
                    componentUpdateInfo.getId(), null, componentUpdateInfo.getVersion()));
            UpdateComponentTask updateComponentTask = new UpdateComponentTask(componentUpdateInfo);
            if (componentUpdateInfo.isForce()) {
                updateComponentTask.runBackground();
                continue;
            }

            String content = Constants.PLUGIN_TITLE + " " + componentUpdateInfo.getName() + " 组件存在可更新版本：" + null + " -> " + componentUpdateInfo.getVersion();
            NotificationUtil.builder(content).sticky()
                    .button("立即更新", event -> {
                        updateComponentTask.setCallback(errorMsg -> {
                            if (errorMsg != null) {
                                MessagesUtil.error(Constants.PLUGIN_TITLE + " " + componentUpdateInfo.getName() + " 组件更新失败：" + errorMsg);
                            }
                        });
                        updateComponentTask.runBackground();
                    }).button("查看更新日志", event -> {
                        BrowserUtil.browse(componentUpdateInfo.getUpdateLogUrl());
                    }).show();
        }
        return componentUpdateInfos;
    }

    private static PluginUpdateInfo getPluginUpdateInfo() {
        if (Math.random() > 0.5) return null;
        return PluginUpdateInfo.builder()
                .name("some update name")
                .version("1.0.0")
                .description("update some stuff")
                .changeNotes("some changeNotes")
                .force(Math.random() > 0.5)
                .updatedWithoutRestart(Math.random() > 0.5)
                .updateLogUrl("http://bing.com/new")
                .downloadUrl("http://127.0.0.1:8000/plugin.zip")
                .size(RandomUtil.randi(10_000L, 1_000_1000L))
                .date(System.currentTimeMillis())
                .build();
    }

    private static List<ComponentUpdateInfo> getComponentUpdateInfo() {
        return Collections.emptyList();
    }
}
