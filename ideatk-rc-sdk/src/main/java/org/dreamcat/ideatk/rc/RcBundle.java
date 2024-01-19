package org.dreamcat.ideatk.rc;

import com.intellij.DynamicBundle;
import org.dreamcat.common.util.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * @author Jerry Will
 * @version 2024-01-18
 */
public class RcBundle extends DynamicBundle {

    private static final String BUNDLE = "messages.RcBundle";
    private static final RcBundle INSTANCE = new RcBundle();

    private RcBundle() {
        super(BUNDLE);
    }

    public static @NotNull @Nls String message(
            @NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
            @NotNull Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static boolean booleanMessage(String key) {
        return StringUtil.isTrueString(message(key));
    }
}
