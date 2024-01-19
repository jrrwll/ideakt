package org.dreamcat.ideatk.rc;

import com.intellij.ui.IconManager;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-18
 */
public class RcIcons {

    public static Icon run_configuration() {
        String path = "run_configuration.svg";
        return load(path, path.hashCode(), 0);
    }

    public static Icon filetype() {
        String path = "filetype.svg";
        return load(path, path.hashCode(), 0);
    }

    private static @NotNull Icon load(@NotNull String path, int cacheKey, int flags) {
        return IconManager.getInstance().loadRasterizedIcon(
                path, RcIcons.class.getClassLoader(), cacheKey, flags);
    }
}
