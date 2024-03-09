package org.dreamcat.ideatk.get_start.action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiLiteralExpression;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.dreamcat.common.Pair;
import org.dreamcat.common.json.JsonUtil;
import org.dreamcat.common.util.Base64Util;
import org.dreamcat.common.util.MapUtil;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.util.StringUtil;
import org.dreamcat.ideatk.util.editor.EditorPsiUtil;
import org.dreamcat.ideatk.util.editor.EditorUtil;
import org.dreamcat.ideatk.util.psi.PsiJavaUtil;
import org.dreamcat.ideatk.util.ui.PopupUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-28
 */
public class JavaStringAction extends AnAction {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Pair<String, String> pair = getSelectJavaString(e);
        if (pair == null) {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return;
        Pair<String, String> pair = getSelectJavaString(e);
        if (pair == null) return;

        String prefix = pair.first();
        List<String> keys = Arrays.stream(pair.second().split(":")).map(key -> {
            if (prefix != null) return prefix + "." + key;
            return key;
        }).collect(Collectors.toList());
        if (keys.size() > 1) {
            JBPopup popup = PopupUtil.createChooser("Select a key", keys, key -> {
                showDesc(key, project, editor);
            });
            popup.showInBestPositionFor(editor);
        } else {
            showDesc(keys.get(0), project, editor);
        }
    }

    private void showDesc(String key, Project project, Editor editor) {
        String value = Base64Util.encodeAsString(key);
        String text = JsonUtil.toJsonWithPretty(MapUtil.of(
                "key", key,
                "value", value));
        Editor jsonEditor = EditorUtil.createJsonEditor(project, text);
        JBPopup popup = PopupUtil.createComponent("Base64", key, jsonEditor.getComponent());
        popup.showInBestPositionFor(editor);
    }

    private Pair<String, String> getSelectJavaString(AnActionEvent e) {
        PsiElement psiElement = EditorUtil.getSelectedElement(e);
        if (psiElement == null) return null;
        String value = PsiJavaUtil.getString(psiElement);
        if (value == null) return null;
        // case "prev", "current"
        PsiLiteralExpression prevExpression = null;
        PsiLiteralExpression expression = (PsiLiteralExpression) psiElement.getParent();
        if (psiElement.getParent().getParent() instanceof PsiExpressionList exprList) {
            for (PsiElement child : exprList.getChildren()) {
                if (child == expression) break;
                if (child instanceof PsiLiteralExpression expr) {
                    if (isJavaStringExpression(expr)) {
                        prevExpression = expr;
                    }
                }
            }
        }
        if (prevExpression != null) {
            return Pair.of(StringUtil.toString(prevExpression.getText()), value);
        }
        // case "current"
        return Pair.of(null, value);
    }

    private boolean isJavaStringExpression(PsiLiteralExpression expression) {
        PsiElement[] children = expression.getChildren();
        if (children.length != 1) return false;
        PsiElement child = children[0];
        if (child instanceof PsiJavaToken javaToken) {
            return javaToken.getTokenType() == JavaTokenType.STRING_LITERAL;
        }
        return false;
    }
}
