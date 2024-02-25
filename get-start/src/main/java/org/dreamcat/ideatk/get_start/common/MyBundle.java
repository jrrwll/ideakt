package org.dreamcat.ideatk.get_start.common;

import com.intellij.DynamicBundle;

/**
 * @author Jerry Will
 * @version 2024-02-03
 */
public class MyBundle extends DynamicBundle {

    private static final String BUNDLE = "messages.MyBundle";
    private static final MyBundle INSTANCE = new MyBundle();

    public MyBundle() {
        super(BUNDLE);
    }

    public static String message(String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
