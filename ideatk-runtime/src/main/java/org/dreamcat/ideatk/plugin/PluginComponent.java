package org.dreamcat.ideatk.plugin;

/**
 * @author Jerry Will
 * @version 2023-12-22
 */
public abstract class PluginComponent {

    public abstract void init();

    public abstract void cleanup();

    public boolean isEnable() {
        return false;
    }

    public void stop() {

    }

    public static void main(String[] args) {

    }
}
