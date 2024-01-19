package org.dreamcat.ideatk.rc;

import static org.dreamcat.ideatk.rc.RcConstants.RC_DESCRIPTION;
import static org.dreamcat.ideatk.rc.RcConstants.RC_EXTENSION;
import static org.dreamcat.ideatk.rc.RcConstants.RC_NAME;
import static org.dreamcat.ideatk.rc.RcConstants.RC_ULTIMATE_ONLY;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts.Label;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.ultimate.PluginVerifier;
import javax.swing.Icon;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcFileType extends LanguageFileType {

    public static final RcFileType INSTANCE = new RcFileType();

    private RcFileType() {
        super(RcLanguage.INSTANCE);
        if (RC_ULTIMATE_ONLY) {
            if (ApplicationManager.getApplication() != null) {
                PluginVerifier.verifyUltimatePlugin();
            }
        }
    }

    @Override
    public @NonNls @NotNull String getName() {
        return RC_NAME;
    }

    @Override
    public @Label @NotNull String getDescription() {
        return RC_DESCRIPTION;
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return RC_EXTENSION;
    }

    @Override
    public Icon getIcon() {
        return RcIcons.filetype();
    }
}
