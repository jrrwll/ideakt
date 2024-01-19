package org.dreamcat.ideatk.util;

import com.intellij.ui.jcef.JBCefBrowser;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class BrowserUtil {

    public static JBCefBrowser create() {

        JBCefBrowser browser = new JBCefBrowser();
        browser.getComponent();

        browser.loadURL("url");

        return browser;
    }

}
