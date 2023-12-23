package util;

import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class BrowserUtil {

    public static JBCefBrowser create() {

        JBCefBrowser browser = new JBCefBrowser();
        browser.getComponent()

        browser.loadURL("url");

        return browser;
    }

}
