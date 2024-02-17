package org.dreamcat.ideatk.get_start.support;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.util.SystemInfo;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.protocol.Message;
import io.sentry.protocol.OperatingSystem;
import io.sentry.protocol.SentryRuntime;
import org.dreamcat.common.util.AssertUtil;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.util.SystemUtil;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class SentryReporter {

    private static final SentryReporter INSTANCE = new SentryReporter(
            SystemUtil.getEnvOrProperty("SENTRY_DSN", "sentry.dsn", ""));

    public static SentryReporter getInstance() {
        return INSTANCE;
    }

    // https://xxx@sentry.io/xxx
    private SentryReporter(String dsn) {
        AssertUtil.requireNotEmpty(dsn, "dsn");
        Sentry.init(options -> {
            options.setDsn(dsn);
            options.setTracesSampleRate(1.0);
            // options.setDebug(true);
        });
    }

    public void submitErrorReport(Throwable error, String description) {
        SentryEvent event = new SentryEvent();

        OperatingSystem os = new OperatingSystem();
        os.setName(SystemInfo.OS_NAME);
        os.setVersion(SystemInfo.OS_VERSION);
        os.setKernelVersion(SystemInfo.OS_ARCH);
        event.getContexts().setOperatingSystem(os);

        final ApplicationInfo applicationInfo = ApplicationInfo.getInstance();
        final String ideName = applicationInfo.getBuild().getProductCode();
        SentryRuntime runtime = new SentryRuntime();
        runtime.setName(ideName);
        runtime.setVersion(applicationInfo.getFullVersion());
        event.getContexts().setRuntime(runtime);

        // User user = new User();
        // user.setId();
        // user.setName();
        // user.setData();
        // event.setUser(user);

        if (ObjectUtil.isNotBlank(description)) {
            Message message = new Message();
            message.setMessage(description);
            event.setMessage(message);
            event.setTag("with-description", "true");
        }
        event.setThrowable(error);

        event.setTag("javaVersion", SystemInfo.JAVA_RUNTIME_VERSION);
        event.setTag("pluginVersion", PluginManager.getPlugin(PluginId.getId("SankuaiToolkit")).getVersion());

        Sentry.captureEvent(event);
    }
}
