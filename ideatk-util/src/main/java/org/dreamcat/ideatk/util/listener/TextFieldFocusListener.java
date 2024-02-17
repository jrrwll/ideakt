package org.dreamcat.ideatk.util.listener;

import com.intellij.ui.JBColor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;
import javax.swing.JTextField;
import lombok.RequiredArgsConstructor;
import org.dreamcat.common.util.ObjectUtil;

/**
 * @author Jerry Will
 * @version 2024-02-11
 */
@RequiredArgsConstructor
public class TextFieldFocusListener implements FocusListener {

    private final String hint;
    private final JTextField textField;

    public static void bindFocus(String hint, JTextField textField) {
        TextFieldFocusListener listener = new TextFieldFocusListener(hint, textField);
        textField.setText(hint);
        textField.setForeground(JBColor.GRAY);
        textField.addFocusListener(listener);
    }

    // 获得焦点
    @Override
    public void focusGained(FocusEvent e) {
        // 清空提示语，设置为黑色字体
        if (Objects.equals(textField.getText(), hint)) {
            textField.setText("");
            textField.setForeground(JBColor.BLACK);
        }
    }

    // 失去焦点
    @Override
    public void focusLost(FocusEvent e) {
        // 如果内容为空，设置提示语
        if (ObjectUtil.isEmpty(textField.getText())) {
            textField.setText(hint);
            textField.setForeground(JBColor.GRAY);
        }
    }
}
