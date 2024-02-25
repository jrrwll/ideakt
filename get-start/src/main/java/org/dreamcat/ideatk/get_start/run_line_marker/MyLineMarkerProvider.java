package org.dreamcat.ideatk.get_start.run_line_marker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.icons.AllIcons.RunConfigurations.TestState;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.awt.event.MouseEvent;
import org.dreamcat.ideatk.util.MessagesUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-02-25
 */
public class MyLineMarkerProvider implements LineMarkerProvider {

    @Override
    public LineMarkerInfo<PsiElement> getLineMarkerInfo(@NotNull PsiElement element) {
        // json string
        if (!element.isValid() || !(element instanceof JsonStringLiteral)) return null;
        // json文件
        PsiFile psiFile = element.getContainingFile();
        if (!(psiFile instanceof JsonFile)) return null;

        return new LineMarkerInfo<>(element, element.getTextRange(),
                TestState.Red2,
                e -> "提示：" + e.getContainingFile().getName(),
                this::navigate, Alignment.RIGHT, () -> "name=" + element.getContainingFile().getName());
    }

    // 定义点击之后的跳转行为
    private void navigate(MouseEvent e, PsiElement element) {
        MessagesUtil.success("textOffset=" + element.getTextOffset() + ", text=" + element.getText());
    }
}
