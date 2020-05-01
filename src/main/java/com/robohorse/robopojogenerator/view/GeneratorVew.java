package com.robohorse.robopojogenerator.view;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorVew {
    private JPanel rootView;
    private JButton generateButton;
    private RSyntaxTextArea textArea;
    private JCheckBox rewriteExistingClassesCheckBox;
    private JTextField className;
    private JScrollPane jsonAreaScrollView;
    private JRadioButton JSONRadioButton;
    private JRadioButton JSONSchemaRadioButton;
    private JRadioButton javaRadioButton;
    private JRadioButton kotlinRadioButton;
    private JPanel sourcePanel;
    private JPanel languagePanel;
    private JPanel frameworkPanel;
    private JList<String> frameworkList;
    private JPanel actionPanel;
    private JPanel propertiesPanel;
    private JPanel commonInfoPanel;
    private JPanel controlsPanel;
    private JScrollPane scrollPropertiesPanel;
    private JCheckBox useTabsIndentation;
    private ButtonGroup sourceGroup;
    private ButtonGroup languageGroup;

    public JCheckBox getUseTabsIndentation() {
        return useTabsIndentation;
    }

    public JRadioButton getJavaRadioButton() {
        return javaRadioButton;
    }

    public JPanel getSourcePanel() {
        return sourcePanel;
    }

    public JRadioButton getJSONRadioButton() {
        return JSONRadioButton;
    }

    public JRadioButton getJSONSchemaRadioButton() {
        return JSONSchemaRadioButton;
    }

    public JPanel getPropertiesPanel() {
        return propertiesPanel;
    }

    public JRadioButton getKotlinRadioButton() {
        return kotlinRadioButton;
    }

    public JPanel getRootView() {
        return rootView;
    }

    public JList<String> getFrameworkList() {
        return frameworkList;
    }

    public JTextField getClassNameTextField() {
        return className;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getClassName() {
        return className;
    }

    public JCheckBox getRewriteExistingClassesCheckBox() {
        return rewriteExistingClassesCheckBox;
    }

    private void createUIComponents() {
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        textArea.setCodeFoldingEnabled(true);
        jsonAreaScrollView = new JScrollPane(textArea);
        try {
            final Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
