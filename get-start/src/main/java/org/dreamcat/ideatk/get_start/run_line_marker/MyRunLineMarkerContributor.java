package org.dreamcat.ideatk.get_start.run_line_marker;

import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.json.psi.JsonArray;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import org.dreamcat.ideatk.get_start.action.JsonAction;
import org.dreamcat.ideatk.get_start.action.MarkdownAction;
import org.dreamcat.ideatk.util.psi.PsiUtil;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownFile;
import org.intellij.plugins.markdown.lang.psi.impl.MarkdownFrontMatterHeaderContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-24
 */
public class MyRunLineMarkerContributor extends RunLineMarkerContributor implements DumbAware {

    @Nullable
    @Override
    public Info getInfo(@NotNull PsiElement element) {
        if (isMarkdown(element)) {
            return createInfo(MarkdownAction.ID);
        }
        if (isJson(element)) {
            return createInfo(JsonAction.ID);
        }
        return null;
    }

    private static Info createInfo(String actionId) {
        AnAction[] actions = {ActionManager.getInstance().getAction(actionId)};
        return new Info(AllIcons.RunConfigurations.TestState.Run,
                actions, element -> "element：" + element);
    }

    private static boolean isMarkdown(PsiElement element) {
        if (!(element instanceof MarkdownFile) ||
                element.getTextRange().getStartOffset() != 0) return false;

        PsiElement[] elems = element.getChildren();
        if (elems.length ==0) return false;
        for (PsiElement elem : elems[0].getChildren()) {
            if (elem instanceof MarkdownFrontMatterHeaderContent) {
                if (((MarkdownFrontMatterHeaderContent)elem).getText().startsWith("title: ")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isJson(PsiElement element) {
        if (!(element instanceof JsonFile) ||
                element.getTextRange().getStartOffset() != 0) return false;

        return !PsiUtil.hasError(element)
                && hasOneArrayOrObjectChild(element);
    }

    private static boolean hasOneArrayOrObjectChild(PsiElement element) {
        PsiElement[] children = element.getChildren();
        if (children.length != 1) return false;
        PsiElement child = children[0];
        return child instanceof JsonArray ||
                child instanceof JsonObject;
    }
}
/*
a JSON PSI

JsonFile: Dummy.json(0,46)
  JsonArray(0,46)
    PsiElement([)('[')(0,1)
    PsiWhiteSpace('\n  ')(1,4)
    JsonObject(4,10)
      ...
 */