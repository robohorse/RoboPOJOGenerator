package com.robohorse.robopojogenerator.view;

import javax.swing.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorVew {
    private JPanel rootView;
    private JButton generateButton;
    private JTextArea textArea;
    private JRadioButton NONERadioButton;
    private JRadioButton jackson2RadioButton;
    private JRadioButton loganSquareRadioButton;
    private JRadioButton GSONRadioButton;
    private JCheckBox rewriteExistingClassesCheckBox;
    private JTextField className;
    private ButtonGroup typeButtonGroup;

    public JPanel getRootView() {
        return rootView;
    }

    public ButtonGroup getTypeButtonGroup() {
        return typeButtonGroup;
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

    public JRadioButton getNONERadioButton() {
        return NONERadioButton;
    }

    public JRadioButton getJackson2RadioButton() {
        return jackson2RadioButton;
    }

    public JRadioButton getLoganSquareRadioButton() {
        return loganSquareRadioButton;
    }

    public JRadioButton getGSONRadioButton() {
        return GSONRadioButton;
    }

    public JCheckBox getRewriteExistingClassesCheckBox() {
        return rewriteExistingClassesCheckBox;
    }

}
