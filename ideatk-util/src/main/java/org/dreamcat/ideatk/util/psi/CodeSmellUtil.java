package org.dreamcat.ideatk.util.psi;

import com.intellij.codeInsight.CodeSmellInfo;
import com.intellij.openapi.vcs.CodeSmellDetector;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Collections;
import java.util.List;

/**
 * @author Jerry Will
 * @version 2024-03-09
 */
public class CodeSmellUtil {

    public static List<CodeSmellInfo> findCodeSmells(PsiFile psiFile) {
        VirtualFile vf;
        if (psiFile == null || (vf = psiFile.getVirtualFile()) == null) {
            return null;
        }

        CodeSmellDetector codeSmellDetector = CodeSmellDetector.getInstance(psiFile.getProject());
        return codeSmellDetector.findCodeSmells(Collections.singletonList(vf));
    }

    public static void showCodeSmellErrors(PsiFile psiFile) {
        List<CodeSmellInfo> codeSmellInfos = findCodeSmells(psiFile);
        if (codeSmellInfos == null) return;

        CodeSmellDetector codeSmellDetector = CodeSmellDetector.getInstance(psiFile.getProject());
        codeSmellDetector.showCodeSmellErrors(codeSmellInfos);
    }
}
