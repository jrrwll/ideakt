package org.dreamcat.ideatk.get_start.handler;

import com.intellij.ide.AppLifecycleListener;
import org.dreamcat.ideatk.util.InvokeUtil;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class MyAppLifecycleListener implements AppLifecycleListener {

    @Override
    public void appStarted() {
        InvokeUtil.executeOnPooledThread(() ->{

        });
    }

    @Override
    public void appWillBeClosed(boolean isRestart) {
        // nop
    }
}
