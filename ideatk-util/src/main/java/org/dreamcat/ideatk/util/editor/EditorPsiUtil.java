package org.dreamcat.ideatk.util.editor;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;

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

    public static JavaSelectInfo getJavaSelectInfo(AnActionEvent e) {
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return null;
        return getJavaSelectInfo(editor);
    }

    public static JavaSelectInfo getJavaSelectInfo(Editor editor) {
        PsiElement psiElement = getSelectedElement(editor);
        if (psiElement == null) return null;

        return new JavaSelectInfo()
                .setElement(psiElement)
                .setParent(psiElement.getParent())
                .setClazz(PsiTreeUtil.getParentOfType(psiElement, PsiClass.class))
                .setMethod(PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class));
    }

    public static String getJavaSelectedString(AnActionEvent e) {
        Editor editor = EditorUtil.getEditor(e);
        if (editor == null) return null;
        return getJavaSelectedString(editor);
    }

    public static String getJavaSelectedString(Editor editor) {
        PsiElement psiElement = getSelectedElement(editor);
        if (psiElement == null) return null;
        if (!(psiElement instanceof PsiJavaToken)) return null;
        IElementType tokenType = ((PsiJavaToken) psiElement).getTokenType();
        if (tokenType == JavaTokenType.STRING_LITERAL) {
            if (psiElement.getParent() instanceof PsiLiteralExpression expression) {
                Object v = expression.getValue();
                return Objects.toString(v);
            }
        }
        return null;
    }

    @Data
    @Accessors(chain = true)
    public static class JavaSelectInfo {

        PsiElement element;
        PsiElement parent;
        PsiClass clazz;
        PsiMethod method;
    }
}
