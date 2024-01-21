package org.dreamcat.ideatk.rc.run;

import static org.dreamcat.ideatk.rc.RcConstants.RC_TEST_FRAMEWORK_NAME;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.ideatk.rc.RcBundle;
import org.dreamcat.ideatk.rc.RcEnvironment;
import org.dreamcat.ideatk.rc.RcExecutionConfig;
import org.dreamcat.ideatk.rc.RcPsiFactory;
import org.dreamcat.ideatk.rc.RcPsiFile;
import org.dreamcat.ideatk.rc.RcVariableSubstitutor;
import org.dreamcat.ideatk.rc.debug.DebugRcProfileState;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.util.xmlb.annotations.Attribute;

/**
 * @author Jerry Will
 * @version 2024-01-19
 */
public class RcRunConfiguration extends LocatableConfigurationBase<Element> {

    private static final Logger log = Logger.getInstance(RcRunConfiguration.class);

    private Settings settings;

    protected RcRunConfiguration(@NotNull Project project,
            @NotNull ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
        this.settings = this.createSettings();
    }

    public @NotNull Settings createSettings() {
        return new Settings();
    }

    public @NotNull Settings getSettings() {
        return settings;
    }

    @Override
    public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RcRunConfigurationEditor(this.getProject());
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment)
            throws ExecutionException {
        return this.getState(executor);
    }

    public @NotNull RunProfileState getState(@NotNull Executor executor) throws ExecutionException {
        Project project = this.getProject();

        try {
            RcExecutionConfig config = this.createConfig(project);
            RcEnvironment environment = this.getEnvironment(project, config.getContext());
            RcVariableSubstitutor substitutor = RcVariableSubstitutor.create(project, environment);
            SMTRunnerConsoleProperties properties = new SMTRunnerConsoleProperties(
                    project, this, RC_TEST_FRAMEWORK_NAME, executor);
            if (executor.getId().equals("Debug")) {
                String name = config.getName(substitutor);
                log.info("Debugging " + name);
                return new DebugRcProfileState(this.getProject(), this.getSettings(), config, properties, substitutor);
            } else {
                String name = config.getName(substitutor);
                log.info("Executing " + name);
                return new RunRcProfileState(this.getProject(), this.getSettings(), config, properties, substitutor);
            }
        } catch (RuntimeConfigurationException e) {
            throw new ExecutionException(e.getMessage());
        }
    }

    private RcExecutionConfig createConfig(@NotNull Project project)
            throws RuntimeConfigurationException {
        if (ObjectUtil.isEmpty(settings.getFilePath()) && settings.getFileText() == null) {
            throw new RuntimeConfigurationException(RcBundle.message("error.file.is.not.configured"));
        }
        PsiFile existingFile = findFileByPath(project, settings.getFilePath());
        PsiFile file;
        if (existingFile == null) {
            if (settings.getFileText() == null) {
                throw new RuntimeConfigurationException(RcBundle.message("error.file.doesn.exists"));
            }
            file = RcPsiFactory.createDummyFile(project, settings.getFileText());
        } else {
            file = existingFile;
        }
        if (file instanceof RcPsiFile && settings.getRunType() == RcRunType.ALL_IN_FILE) {
            return new RcFileExecutionConfig((RcPsiFile)file, settings.isShowInformationAboutRequest());
        } else {
            return new RcSingleExecutionConfig(findRequestInFile(file, this.settings), settings.isShowInformationAboutRequest());
        }
    }

    public static class Settings {
        private String environment = "<Default Environment>";
        private String debugger = null;
        private String path = null;
        private int index = 1;
        private String requestIdentifier = "";
        private RcRunType runType = RcRunType.ALL_IN_FILE;
        private String fileText;
        private boolean showInformationAboutRequest = true;

        @Attribute("environment")
        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        @Attribute("debugger")
        public String getDebugger() {
            return debugger;
        }

        public void setDebugger(String debugger) {
            this.debugger = debugger;
        }

        @Attribute("path")
        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Attribute("index")
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Attribute("requestIdentifier")
        public String getRequestIdentifier() {
            return requestIdentifier;
        }

        public void setRequestIdentifier(String requestIdentifier) {
            this.requestIdentifier = requestIdentifier;
        }

        @Attribute("runType")
        public RcRunType getRunType() {
            return runType;
        }

        public void setRunType(RcRunType runType) {
            this.runType = runType;
        }

        public String getFileText() {
            return fileText;
        }

        public void setFileText(String fileText) {
            this.fileText = fileText;
        }

        public boolean isShowInformationAboutRequest() {
            return showInformationAboutRequest;
        }

        public void setShowInformationAboutRequest(boolean showInformationAboutRequest) {
            this.showInformationAboutRequest = showInformationAboutRequest;
        }
    }
}
