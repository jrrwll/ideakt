package org.dreamcat.ideatk.get_start.common;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.extensions.PluginId;
import java.io.File;
import java.util.Objects;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class Constants {

    public static final String IDEA_VERSION = ApplicationInfo.getInstance().getFullVersion();

    public static PluginId PLUGIN_ID = PluginId.getId("org.dreamcat.ideatk.getstart");
    public static IdeaPluginDescriptor PLUGIN_DESCRIPTOR = Objects.requireNonNull(
            PluginManagerCore.getPlugin(PLUGIN_ID));
    public static final String PLUGIN_TITLE = PLUGIN_DESCRIPTOR.getName();
    public static final String PLUGIN_VERSION = PLUGIN_DESCRIPTOR.getVersion();
    public static final File PLUGIN_DIR = PLUGIN_DESCRIPTOR.getPluginPath().toFile();
}
