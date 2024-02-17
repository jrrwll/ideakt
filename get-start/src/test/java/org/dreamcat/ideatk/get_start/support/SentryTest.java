package org.dreamcat.ideatk.get_start.support;

import io.sentry.Sentry;
import org.junit.Test;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class SentryTest {

    @Test
    public void test() {
        Sentry.init(options -> {
            options.setDsn(System.getenv("SENTRY_DSN"));
            // Set tracesSampleRate to 1.0 to capture 100% of transactions for performance monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
        });

        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
