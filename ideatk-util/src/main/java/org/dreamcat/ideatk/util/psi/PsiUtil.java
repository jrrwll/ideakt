package org.dreamcat.ideatk.util.psi;

import com.intellij.ide.scratch.ScratchUtil;
import com.intellij.ide.scratch.ScratchesSearchScope;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.pointers.VirtualFilePointer;
import com.intellij.openapi.vfs.pointers.VirtualFilePointerContainer;
import com.intellij.openapi.vfs.pointers.VirtualFilePointerListener;
import com.intellij.openapi.vfs.pointers.VirtualFilePointerManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopesCore;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.util.PsiUtilCore;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.dreamcat.common.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-03
 */
public class PsiUtil {

    public static PsiFile find(Project project, VirtualFile virtualFile) {
        return PsiManager.getInstance(project).findFile(virtualFile);
    }

    public static PsiFile find(Project project, Document document) {
        return PsiDocumentManager.getInstance(project).getPsiFile(document);
    }

    public static PsiFile find(Project project, String path) {
        if (project == null || ObjectUtil.isEmpty(path)) return null;

        String url = path;
        String protocol = VirtualFileManager.extractProtocol(path);
        if (ObjectUtil.isEmpty(protocol)) {
            url = VfsUtilCore.pathToUrl(path);
        }

        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(url);
        if (file == null) return null;
        return PsiManager.getInstance(project).findFile(file);
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static String getFilePath(PsiFile psiFile) {
        VirtualFile vf = psiFile.getVirtualFile();
        if (vf != null) {
            return vf.getPath();
        }
        return null;
    }

    public static @NotNull Collection<VirtualFile> getVirtualFilesByName(Project project, String name) {
        return FilenameIndex.getVirtualFilesByName(name, GlobalSearchScope.allScope(project));
    }

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

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static boolean isFileOfType(VirtualFile file, FileType type) {
        return FileTypeRegistry.getInstance().isFileOfType(file, type);
    }

    public static VirtualFilePointerContainer createPointerContainer(
            Disposable disposable, Consumer<VirtualFilePointer[]> onValidityChanged) {
        return VirtualFilePointerManager.getInstance()
                .createContainer(disposable, new VirtualFilePointerListener() {
                    @Override
                    public void validityChanged(VirtualFilePointer @NotNull [] pointers) {
                        onValidityChanged.accept(pointers);
                    }
                });
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public static boolean hasError(@NotNull PsiFile psiFile) {
        AtomicBoolean found = new AtomicBoolean();
        PsiRecursiveElementWalkingVisitor visitor = new PsiRecursiveElementWalkingVisitor() {
            @Override
            public void visitErrorElement(@NotNull PsiErrorElement element) {
                found.set(true);
                stopWalking();
            }
        };
        visitor.visitFile(psiFile);
        return found.get();
    }

    public static boolean hasError(@NotNull PsiElement psiElement) {
        AtomicBoolean found = new AtomicBoolean();
        PsiRecursiveElementWalkingVisitor visitor = new PsiRecursiveElementWalkingVisitor() {
            @Override
            public void visitErrorElement(@NotNull PsiErrorElement element) {
                found.set(true);
                stopWalking();
            }
        };
        visitor.visitElement(psiElement);
        return found.get();
    }


}
