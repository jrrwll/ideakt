package org.dreamcat.ideatk.get_start.handler;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JFrame;
import lombok.RequiredArgsConstructor;
import org.dreamcat.ideatk.get_start.common.Context;

/**
 * @author Jerry Will
 * @version 2024-02-17
 */
@RequiredArgsConstructor
public class MyWindowFocusListener implements WindowFocusListener {

    private static final Map<Project, MyWindowFocusListener> listenerMap = new ConcurrentHashMap<>();

    private final Project project;

    @Override
    public void windowGainedFocus(WindowEvent e) {
        Context.setRecentlyFocusedProject(project);
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        // nop
    }

    public static void add(Project project) {
        Context.setRecentlyFocusedProject(project);

        listenerMap.computeIfAbsent(project, p -> {
            JFrame frame = WindowManager.getInstance().getFrame(project);
            if (frame == null) return null;
            MyWindowFocusListener listener = new MyWindowFocusListener(project);
            frame.addWindowFocusListener(listener);
            return listener;
        });
    }

    public static void remove(Project project) {
        MyWindowFocusListener listener = listenerMap.get(project);
        if (listener == null) return;
        JFrame frame = WindowManager.getInstance().getFrame(project);
        if (frame == null) return;

        frame.removeWindowFocusListener(listener);
        listenerMap.remove(project);

        if (Context.getRecentlyFocusedProject() == project) {
            Context.setRecentlyFocusedProject(null);
        }
    }
}
