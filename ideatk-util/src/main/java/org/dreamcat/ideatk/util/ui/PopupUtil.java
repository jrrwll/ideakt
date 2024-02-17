package org.dreamcat.ideatk.util.ui;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.popup.AbstractPopup;
import com.intellij.util.Consumer;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class PopupUtil {

    public static <T> JBPopup createChooser(String title, List<T> items, Consumer<? super T> callback) {
        return JBPopupFactory.getInstance()
                .createPopupChooserBuilder(items)
                .setTitle(title)
                .setItemChosenCallback(callback)
                .createPopup();
    }

    public static <T> JBPopup createComponent(String title, String adText, JComponent content) {
        JBPopup popup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(content, null)
                .setTitle(title)
                .setAdText(adText, SwingConstants.CENTER)
                .createPopup();
        Dimension dim = ((AbstractPopup)popup).getTitle().getPreferredSize();
        popup.setMinimumSize(dim);
        return popup;
    }
}
