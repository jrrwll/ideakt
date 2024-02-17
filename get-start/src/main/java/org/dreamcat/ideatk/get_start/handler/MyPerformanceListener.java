package org.dreamcat.ideatk.get_start.handler;

import com.intellij.diagnostic.IdePerformanceListener;
import com.intellij.openapi.diagnostic.Logger;
import java.io.File;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean ;
import org.dreamcat.common.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
public class MyPerformanceListener implements IdePerformanceListener {

    private static final Logger log = Logger.getInstance(MyPerformanceListener.class);

    @Override
    public void uiFreezeStarted(@NotNull File reportDir) {
        OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        log.info(StringUtil.format("arch={}, load avg={}, cpu load={}, process load={}",
                bean.getArch(), bean.getSystemLoadAverage(), bean.getCpuLoad(),
                bean.getProcessCpuLoad()));
    }

    @Override
    public void uiFreezeFinished(long durationMs, @Nullable File reportDir) {
        log.info(StringUtil.format("performance log: {}, cost: {}", reportDir, durationMs));
    }
}
