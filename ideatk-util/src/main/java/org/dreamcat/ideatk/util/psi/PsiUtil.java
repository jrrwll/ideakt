package org.dreamcat.ideatk.util.psi;

import com.intellij.openapi.Disposable;
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
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import java.util.function.Consumer;
import org.dreamcat.common.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jerry Will
 * @version 2024-02-03
 */
public class PsiUtil {

    public static @Nullable PsiFile findFileByPath(Project project, String path) {
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

    public static boolean isFileOfType(VirtualFile file, FileType type) {
        return FileTypeRegistry.getInstance().isFileOfType(file, type);
    }

    /**
     * @see PsiFile#getVirtualFile()
     */
    public static PsiFile toPsiFile(Project project, VirtualFile virtualFile) {
        return PsiManager.getInstance(project).findFile(virtualFile);
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
}
