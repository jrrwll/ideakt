package org.dreamcat.ideatk.util;

import com.intellij.openapi.ide.CopyPasteManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class CopyPasteUtil {

    public static void setClipboard(String data) {
        Transferable content = new StringSelection(data);
        CopyPasteManager.getInstance().setContents(content);
    }

    public static String readString() {
        Transferable content = CopyPasteManager.getInstance().getContents();
        if (content == null) return null;
        if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                return (String) content.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception ignore){}
        }
        return null;
    }
}
