package org.dreamcat.ideatk.get_start.common;

import com.intellij.openapi.util.IconLoader;
import javax.swing.Icon;

/**
 * @author Jerry Will
 * @version 2024-02-26
 */
public class MyIcons {

    // /icons/some.svg
    private static Icon load(String name) {
        return IconLoader.getIcon(name, MyIcons.class);
    }
}
