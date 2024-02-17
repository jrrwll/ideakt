package org.dreamcat.ideatk.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.dreamcat.common.function.IBiConsumer;
import org.dreamcat.common.function.IConsumer;
import org.dreamcat.common.function.IFunction;
import org.dreamcat.common.util.ExceptionUtil;
import org.dreamcat.common.util.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-16
 */
public class ProgressTaskUtil {

    private static final Logger log = Logger.getInstance(ProgressTaskUtil.class);

    public static void runBackground(
            Project project, String title,
            @NotNull IConsumer<ProgressIndicator, ?> task,
            IConsumer<String, ?> callback) {
        runBackground(project, title, true, task, callback);
    }

    public static void runBackground(
            Project project, String title, boolean canCancelled,
            @NotNull IConsumer<ProgressIndicator, ?> task,
            IConsumer<String, ?> callback) {
        runBackground(project, title, canCancelled, pi -> {
            task.accept(pi);
            return null;
        }, (res, msg) -> {
            if (callback != null) {
                callback.accept(msg);
            }
        });
    }

    public static <T> void runBackground(
            Project project, String title,
            @NotNull IFunction<ProgressIndicator, T, ?> task,
            IBiConsumer<T, String, ?> callback) {
        runBackground(project, title, true, task, callback);
    }

    public static <T> void runBackground(
            Project project, String title, boolean canCancelled,
            @NotNull IFunction<ProgressIndicator, T, ?> task,
            IBiConsumer<T, String, ?> callback) {
        ProgressManager.getInstance().run(new Task.Backgroundable(project, title, canCancelled) {
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    progressIndicator.setIndeterminate(false);
                    if (!progressIndicator.isRunning()) {
                        progressIndicator.start();
                    }

                    T result = task.apply(progressIndicator);
                    if (callback != null) callback.accept(result, null);
                } catch (Exception e) {
                    log.error(StringUtil.format("{}执行失败：{}",
                            title, ExceptionUtil.getRootCause(e).getMessage()));
                    String errorMsg;
                    if (e instanceof ProcessCanceledException) {
                        errorMsg = "已取消";
                    } else {
                        errorMsg = e.getMessage();
                    }
                    InvokeUtil.callback(() -> callback.accept(null, errorMsg));
                } finally {
                    if (progressIndicator.isRunning()) {
                        progressIndicator.stop();
                    }
                }
            }
        });
    }
}
