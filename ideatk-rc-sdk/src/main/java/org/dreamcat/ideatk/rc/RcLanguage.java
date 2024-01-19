package org.dreamcat.ideatk.rc;

import static org.dreamcat.ideatk.rc.RcConstants.RC_NAME;

import com.intellij.lang.Language;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcLanguage extends Language {

    public static final RcLanguage INSTANCE = new RcLanguage();

    protected RcLanguage() {
        super(RC_NAME);
    }
}
