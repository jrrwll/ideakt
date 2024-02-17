package org.dreamcat.ideatk.util.psi;

import com.intellij.ide.scratch.ScratchUtil;
import com.intellij.ide.scratch.ScratchesSearchScope;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopesCore;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.util.PsiUtilCore;

/**
 * @author Jerry Will
 * @version 2024-02-03
 */
public class PsiSearchUtil {

    public static GlobalSearchScope getSearchScope(Project project, PsiFile file) {
        GlobalSearchScope projectScope = ProjectScope.getContentScope(project);
        VirtualFile vf = PsiUtilCore.getVirtualFile(file);
        if (file != null && vf != null && !ScratchUtil.isScratch(vf) &&
                !projectScope.contains(vf)) {
            PsiDirectory parent = file.getParent();
            if (parent != null) {
                return GlobalSearchScopesCore.directoryScope(parent, false);
            }
        }

        if (ScratchUtil.isScratch(vf)) {
            return projectScope.uniteWith(ScratchesSearchScope.getScratchesScope(project));
        } else {
            return projectScope;
        }
    }
}
