package org.dreamcat.ideatk.get_start.run_line_marker;

import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.json.psi.JsonFile;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import org.dreamcat.ideatk.get_start.action.JsonAction;
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
        // json文件，第一个字符所在行
        if (!(element instanceof JsonFile) || !element.isValid() ||
                element.getTextRange().getStartOffset() != 0) return null;

        AnAction[] actions = {ActionManager.getInstance().getAction(JsonAction.ID)};
        return new Info(AllIcons.RunConfigurations.TestState.Run,
                actions, this::provideTooltip);
    }

    private String provideTooltip(PsiElement element) {
        return "element：" + element;
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