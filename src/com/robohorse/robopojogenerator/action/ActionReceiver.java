package com.robohorse.robopojogenerator.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.support.GeneratorViewCreator;
import com.robohorse.robopojogenerator.support.MessageService;
import com.robohorse.robopojogenerator.support.PathValidator;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionReceiver {
    private PathValidator pathValidator = new PathValidator();
    private MessageService messageService = new MessageService();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator(messageService);

    public void onActionHandled(AnActionEvent event) {
        try {
            proceed(event);
        } catch (RoboPluginException exception) {
            messageService.onPluginExceptionHandled(exception);
        }
    }

    private void proceed(AnActionEvent event) throws RoboPluginException {
        final String path = pathValidator.checkPath(event);
        if (null != path) {
            viewCreator.setGuiFormEventListener(new GuiFormEventListener() {
                @Override
                public void onJsonDataObtained(String content) {

                }
            });
            viewCreator.showView();
        }
    }


//                if (SettingsManager.isComposed(path)) {
//                    SettingsManager.removeComposedFolder(path);
//                } else {
//                    SettingsManager.addComposedFolder(path);
//                }
//
//                Project project = event.getData(CommonDataKeys.PROJECT);
//                if (project != null) {
//                    ProjectView.getInstance(project).refresh();
//                }
}
