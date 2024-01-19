package org.dreamcat.ideatk.util;

import com.intellij.openapi.application.ApplicationManager;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class AsyncUtil {

    public static void invokeLater(Runnable r) {
        ApplicationManager.getApplication().invokeLater(r);
    }
}
