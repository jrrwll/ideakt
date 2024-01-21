package org.dreamcat.ideatk.rc;

import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.ui.popup.ListSeparator;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiFile;
import com.intellij.ui.HyperlinkLabel;
import com.intellij.util.containers.ContainerUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.dreamcat.ideatk.rc.run.RcFileExecutionConfig;
import org.jetbrains.annotations.NotNull;

/**
 * @author Jerry Will
 * @version 2024-01-20
 */
public class RcRunFileAction extends AnAction implements DumbAware {

    @Override
    public boolean isDumbAware() {
        return true;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        Project project = e.getProject();
        if (project == null || !(file instanceof RcPsiFile)) return;

        Pair<Map<String, AnAction>, String> pair = getActionsForPopup(project, (RcPsiFile) file);
        Map<String, AnAction> actions = pair.getFirst();
        if (actions.size() == 1) {
            AnAction action = (AnAction) ((Map.Entry<?, ?>) ContainerUtil.getFirstItem(actions.entrySet())).getValue();
            action.actionPerformed(e);
            return;
        }

        String title = RcBundle.message("mrpc.request.execute.environment.popup.header");
        String firstEnvAction = pair.getSecond();
        ListPopup runActionsPopup = JBPopupFactory.getInstance()
                .createListPopup(new BaseListPopupStep<>(title, new ArrayList<>(actions.keySet())) {
                    public ListSeparator getSeparatorAbove(String value) {
                        return firstEnvAction != null && firstEnvAction.equals(value) ? new ListSeparator() : null;
                    }

                    public PopupStep<?> onChosen(String requestName, boolean finalChoice) {
                        return this.doFinalStep(() -> {
                            (actions.get(requestName)).actionPerformed(e);
                        });
                    }
                });
        HyperlinkLabel hyperlinkLabel = e.getDataContext()
                .getData(DataKeys.RUN_ALL_TOOLBAR_HYPERLINK_LABEL);
        if (hyperlinkLabel != null) {
            runActionsPopup.showUnderneathOf(hyperlinkLabel);
        } else {
            runActionsPopup.showInBestPositionFor(e.getDataContext());
        }
    }

    private static Pair<Map<String, AnAction>, String> getActionsForPopup(Project project, RcPsiFile file) {
        RcFileExecutionConfig config = new RcFileExecutionConfig(file);
        String actionName = DefaultRunExecutor.getRunExecutorInstance().getActionName();

        List<String> envs = new ArrayList(EnvironmentsProvider.provideEnvironments(file));
        Map<String, AnAction> actions = new LinkedHashMap<>();
        if (!envs.isEmpty()) {
            actions.put(RcBundle.message("execute.with.default.environment", actionName),
                    new RunRcRequestAction.RunRequestWithDefaultEnvAction(config,
                            (RcRequestExecutorExtension) null));
        }
        actions.put(RcBundle.message("execute.with.no.environment", actionName),
                new RunRcRequestAction.RunRequestWithoutEnvAction(config, (RcRequestExecutorExtension) null));
        Collections.sort(envs);

        for (String env : envs) {
            actions.put(RcBundle.message("execute.with.environment", actionName, env),
                    new RunRcRequestAction.RunRequestWithEnvAction(config, env, (RcRequestExecutorExtension) null));
        }

        String firstEnvAction = null;
        if (!envs.isEmpty()) {
            firstEnvAction = RcBundle.message("execute.with.environment",
                    actionName, ContainerUtil.getFirstItem(envs));
        }
        return Pair.create(actions, firstEnvAction);
    }
}
