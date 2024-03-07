package org.dreamcat.ideatk.util.editor;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author Jerry Will
 * @version 2024-01-27
 */
public class EditorPsiUtil {

    public static PsiFile getFile(AnActionEvent e) {
        return e.getData(CommonDataKeys.PSI_FILE);
    }

    public static PsiFile getFile(Editor editor) {
        Project project = editor.getProject();
        if (project == null) return null;
        return PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
    }

    public static PsiElement getSelectedElement(AnActionEvent e) {
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return null;
        return getSelectedElement(editor);
    }

    public static PsiElement getSelectedElement(Editor editor) {
        PsiFile psiFile = getFile(editor);
        if (psiFile == null) return null;
        return psiFile.findElementAt(editor.getCaretModel().getOffset());
    }
}
