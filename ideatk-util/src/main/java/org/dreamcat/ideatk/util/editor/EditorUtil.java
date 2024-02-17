package org.dreamcat.ideatk.util.editor;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import java.awt.event.MouseEvent;
import org.dreamcat.ideatk.util.CopyPasteUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-27
 */
public class EditorUtil {

    public static Editor getEditor(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return null;

        return e.getData(PlatformDataKeys.EDITOR);
    }

    public static Editor createJsonEditor(Project project, String text) {
        FileType jsonFileType = FileTypeManager.getInstance().getFileTypeByExtension("json");

        EditorFactory editorFactory = EditorFactory.getInstance();
        Document doc = editorFactory.createDocument(text);
        doc.setReadOnly(false);

        Editor editor = editorFactory.createEditor(doc, project, jsonFileType, false);
        EditorSettings editorSettings = editor.getSettings();
        editorSettings.setVirtualSpace(false);
        editorSettings.setWheelFontChangeEnabled(true);
        editorSettings.setLineMarkerAreaShown(false);
        editorSettings.setIndentGuidesShown(false);
        editorSettings.setFoldingOutlineShown(true);
        editorSettings.setAdditionalColumnsCount(3);
        editorSettings.setAdditionalLinesCount(0);
        editorSettings.setCaretRowShown(true);
        editorSettings.setLineNumbersShown(false);

        EditorColorsScheme colorsScheme = editor.getColorsScheme();
        int fontSize = colorsScheme.getEditorFontSize();
        // colorsScheme.setEditorFontSize(fontSize - 2);

        EditorHighlighter highlighter = EditorHighlighterFactory.getInstance()
                .createEditorHighlighter(JsonFileType.INSTANCE, colorsScheme, project);
       ((EditorEx) editor).setHighlighter(highlighter);

        editor.addEditorMouseListener(new EditorMouseListener() {
            @Override
            public void mousePressed(@NotNull EditorMouseEvent e) {
                MouseEvent me = e.getMouseEvent();
                // 双击复制
                if (me.getClickCount() > 0 && me.getClickCount() % 2 == 0) {
                    CopyPasteUtil.setClipboard(editor.getDocument().getText());
                }
            }
        });
        return editor;
    }
}
