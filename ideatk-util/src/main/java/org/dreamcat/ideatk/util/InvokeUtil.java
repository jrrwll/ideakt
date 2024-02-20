package org.dreamcat.ideatk.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Ref;
import java.awt.EventQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.SwingUtilities;
import lombok.SneakyThrows;
import org.dreamcat.common.function.IVoidConsumer;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-15
 */
public class InvokeUtil {

    private static final Logger log = Logger.getInstance(InvokeUtil.class);

    public static void invokeAndWait(Runnable runnable) {
        ApplicationManager.getApplication().invokeAndWait(runnable);
    }

    @SneakyThrows
    public static <T> T invokeAndWait(Callable<T> callable) {
        Ref<T> resultRef = Ref.create();
        Ref<Exception> exceptionRef = Ref.create();
        ApplicationManager.getApplication().invokeAndWait(() -> {
            try {
                resultRef.set(callable.call());
            } catch (Exception e) {
                exceptionRef.set(e);
            }
        });
        if (exceptionRef.get() != null) {
            throw (Exception)exceptionRef.get();
        } else {
            return resultRef.get();
        }
    }

    public static void invokeLatter(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            ApplicationManager.getApplication().invokeLater(runnable);
        }
    }

    public static void executeOnPooledThread(Runnable r) {
        ApplicationManager.getApplication().executeOnPooledThread(r);
    }

    public static <T> Future<T> executeOnPooledThread(Callable<T> c) {
        return ApplicationManager.getApplication().executeOnPooledThread(c);
    }

    public static void callback(@Nullable IVoidConsumer<?> callback) {
        if (callback == null) return;
        EventQueue.invokeLater(() -> {
            try {
                callback.accept();
            } catch (Exception e) {
                log.error("callback failed", e);
            }
        });
    }
}
