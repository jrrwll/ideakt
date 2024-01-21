package org.dreamcat.ideatk.rc.run;

import static org.dreamcat.ideatk.rc.RcConstants.RC_NAME;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.dreamcat.ideatk.rc.RcFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * @author Jerry Will
 * @version 2024-01-18
 */
public class RcRunConfigurationFactory extends ConfigurationFactory {

    protected RcRunConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return RC_NAME;
    }

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return project.isDefault() || FileTypeIndex.containsFileOfType(RcFileType.INSTANCE, GlobalSearchScope.projectScope(project));
    }

    @Override
    public boolean isEditableInDumbMode() {
        return true;
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new RcRunConfiguration(project, this, "");
    }
}
