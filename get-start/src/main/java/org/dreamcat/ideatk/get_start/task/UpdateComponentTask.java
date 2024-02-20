package org.dreamcat.ideatk.get_start.task;

import com.intellij.openapi.progress.ProgressIndicator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.dreamcat.common.function.IConsumer;
import org.dreamcat.ideatk.util.ProgressTaskUtil;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
@Setter
@RequiredArgsConstructor
public class UpdateComponentTask {

    private final ComponentUpdateInfo componentUpdateInfo;
    private IConsumer<String, ?> callback;

    public void runBackground() {
        ProgressTaskUtil.runBackground(null,
                componentUpdateInfo.getName() + "组件更新", true,
                this::updateComponent, callback);
    }

    private void updateComponent(ProgressIndicator progressIndicator) throws Exception {
        progressIndicator.setText2("组件 " + componentUpdateInfo.getId() + " 正在更新中...");
        // http get progress...
        // progressIndicator.setText2("正在下载 " + componentUpdateInfo.getId() + " " + progress.getCurrent() + "/" + progress.getTotal());
        // progressIndicator.setFraction(progress.getProgress());

        progressIndicator.setText2("组件 " + componentUpdateInfo.getId() + " 更新完成");
        progressIndicator.setFraction(1.0);

        progressIndicator.stop(); // finally

    }
}
