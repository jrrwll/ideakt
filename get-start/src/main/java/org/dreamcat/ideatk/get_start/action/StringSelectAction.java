package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import java.util.Arrays;
import java.util.List;
import org.dreamcat.common.json.JsonUtil;
import org.dreamcat.common.util.Base64Util;
import org.dreamcat.common.util.MapUtil;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil;
import org.dreamcat.ideatk.util.editor.EditorUtil;
import org.dreamcat.ideatk.util.ui.PopupUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class StringSelectAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        String selectedText = getSelectedText(e);
        if (selectedText == null) {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return;
        String selectedText = getSelectedText(e);
        if (selectedText == null) return;

        List<String> keys = Arrays.asList(selectedText.split(":"));
        if (keys.size() > 1) {
            JBPopup popup = PopupUtil.createChooser("Select a key", keys, key -> {
                showDesc(key, project, editor);
            });
            popup.showInBestPositionFor(editor);
        } else {
            showDesc(keys.get(0), project, editor);
        }
    }

    private String getSelectedText(AnActionEvent e) {
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return null;

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (ObjectUtil.isEmpty(selectedText)) {
            selectedText = EditorPsiUtil.getJavaSelectedString(editor);
        }
        return selectedText;
    }

    private void showDesc(String key, Project project, Editor editor) {
        String v = Base64Util.encodeAsString(key);
        String text = JsonUtil.toJsonWithPretty(MapUtil.of("content", v));

        Editor jsonEditor = EditorUtil.createJsonEditor(project, text);
        JBPopup popup = PopupUtil.createComponent("Base64", v, jsonEditor.getComponent());
        popup.showInBestPositionFor(editor);
    }
}
