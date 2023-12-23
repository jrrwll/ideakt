package util;

import com.intellij.ui.jcef.JBCefClient;
import java.net.URL;
import java.util.Objects;
import org.cef.browser.CefFrame;
import org.cef.network.CefRequest;

/**
 * @author Jerry Will
 * @version 2023-12-23
 */
public class JBCefUtil {

    public static boolean isCorsRequest(CefFrame cefFrame, CefRequest cefRequest) {
        String frameUrl = cefFrame.getURL();
        String requestUrl = cefRequest.getURL();
        if (frameUrl == null || requestUrl == null) return false;

        try {
            URL frameUrlObj = new URL(frameUrl);
            URL requestUrlObj = new URL(requestUrl);
            return Objects.equals(frameUrlObj.getProtocol(), requestUrlObj.getProtocol()) &&
                    Objects.equals(frameUrlObj.getHost(), requestUrlObj.getHost()) &&
                    Objects.equals(frameUrlObj.getPort(), requestUrlObj.getPort());
        } catch (Exception ignore) {
            return false;
        }
    }

    {
        // file:///jbcefbrowser/
        JBCefClient client = new JBCefClient();

        CefRequest cefRequest;
        cefRequest.getHeaderMap(null);

        CefFrame cefFrame;
        cefFrame.executeJavaScript("", cefFrame.getURL(), 0);

    }

}
