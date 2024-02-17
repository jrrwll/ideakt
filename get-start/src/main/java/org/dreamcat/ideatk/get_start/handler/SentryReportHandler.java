package org.dreamcat.ideatk.get_start.handler;

import com.intellij.diagnostic.AbstractMessage;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.util.NlsActions.ActionText;
import com.intellij.util.Consumer;
import java.awt.Component;
import org.dreamcat.ideatk.get_start.support.SentryReporter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-01-25
 */
public class SentryReportHandler extends ErrorReportSubmitter {

    @Override
    public @ActionText @NotNull String getReportActionText() {
        return "&Report to Sentry";
    }

    @Override
    public boolean submit(IdeaLoggingEvent @NotNull [] events, @Nullable String additionalInfo,
            @NotNull Component parentComponent, @NotNull Consumer<? super SubmittedReportInfo> consumer) {
        for (IdeaLoggingEvent event : events) {
            Throwable e = event.getThrowable();
            Object data = event.getData();
            if (data instanceof AbstractMessage) {
                e = ((AbstractMessage)data).getThrowable();
            }
            SentryReporter.getInstance().submitErrorReport(e, additionalInfo);
        }
        return true;
    }
}
