package org.dreamcat.ideatk.get_start.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Jerry Will
 * @version 2024-01-30
 */
public class MyForm {

    public JPanel panel;
    public JTextField textField1;
    public JTextField textField2;
    public JTextArea textArea1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("My Form");
        frame.setContentPane(new MyForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
