package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.project.Project;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.dreamcat.ideatk.util.NotificationUtil;

/**
 * @author Jerry Will
 * @version 2024-01-31
 */
public class MyDialog extends JDialog {

    private static final ScheduledExecutorService executors =
            Executors.newSingleThreadScheduledExecutor();

    public JPanel contentPane;
    public JTextField textField1;
    public JTextField textField2;
    public JButton okButton;
    public JLabel label1;

    public MyDialog() {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Forth Dialog");

        // call onCancel() on close
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESC
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        okButton.addActionListener(e -> {
            executors.schedule(() -> NotificationUtil.notify(e.getActionCommand()),
                    1, TimeUnit.SECONDS);
            onCancel();
        });
        textField1.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {
                label1.setText(event.paramString());
            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });
    }

    public void init(Project project) {

    }

    private void onCancel() {
        dispose();
    }
}
