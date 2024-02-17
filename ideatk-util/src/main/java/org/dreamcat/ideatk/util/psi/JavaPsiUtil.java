package org.dreamcat.ideatk.util.psi;

import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.search.searches.ReferencesSearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-27
 */
public class JavaPsiUtil {

    public static Module getModule(PsiClass clazz) {
        Project project = clazz.getProject();
        ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        return fileIndex.getModuleForFile(clazz.getContainingFile().getVirtualFile());
    }

    @NotNull
    public static List<VirtualFile> getSourceRoots(PsiClass clazz) {
        Module module = getModule(clazz);
        return Arrays.asList(ModuleRootManager.getInstance(module)
                .orderEntries().getSourceRoots());
    }

    @NotNull
    public static List<VirtualFile> getClassesRoots(PsiClass clazz) {
        Module module = getModule(clazz);
        return Arrays.asList(ModuleRootManager.getInstance(module)
                .orderEntries().getClassesRoots());
    }

    @NotNull
    public static List<PsiClass> searchImpl(PsiClass clazz) {
        return new ArrayList<>(ClassInheritorsSearch.search(clazz).findAll());
    }

    @NotNull
    public static List<PsiReference> searchRefer(PsiClass clazz) {
        return new ArrayList<>((ReferencesSearch.search(clazz).findAll()));
    }

    @NotNull
    public static Map<String, PsiAnnotationMemberValue> getAnnotationValue(PsiAnnotation psiAnnotation) {
        Map<String, PsiAnnotationMemberValue> m = new HashMap<>();
        for (JvmAnnotationAttribute attribute : psiAnnotation.getAttributes()) {
            if (attribute instanceof PsiNameValuePair) {
                m.put(attribute.getAttributeName(), ((PsiNameValuePair) attribute).getValue());
            }
        }
        return m;
    }

    @NotNull
    public static List<VirtualFile> findInLibrary(PsiClass clazz, String filename) {
        String qualifiedName = clazz.getQualifiedName();
        if (qualifiedName == null) return Collections.emptyList();
        Project project = clazz.getProject();

        Library library = LibraryUtil.findLibraryByClass(qualifiedName, project);
        if (library == null) return Collections.emptyList();

        List<VirtualFile> virtualFiles = new ArrayList<>();
        VirtualFile[] vfs = library.getFiles(OrderRootType.CLASSES);
        for (VirtualFile vf : vfs) {
            VirtualFile virtualFile = vf.findChild(filename);
            if (virtualFile != null) {
                virtualFiles.add(virtualFile);
            }
        }
        return virtualFiles;
    }
}
