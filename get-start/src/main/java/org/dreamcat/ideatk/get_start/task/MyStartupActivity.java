package org.dreamcat.ideatk.get_start.task;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
public class MyStartupActivity implements ProjectActivity {

    private static final Logger log = Logger.getInstance(UpdateCheckTask.class);

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            if (!project.isDisposed()) {
                try {
                    // 加载所有组件

                    // 检查更新
                    (new UpdateCheckTask()).runBackground();
                } catch (Throwable e) {
                    log.error("project startup activity failed", e);
                }

            }
        });
        return null;
    }
}
