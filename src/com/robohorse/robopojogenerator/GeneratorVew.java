package com.robohorse.robopojogenerator;

import javax.swing.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratorVew {
    private JPanel rootView;
    private JRadioButton radioGson;
    private JRadioButton radioLogan;
    private JButton generateButton;
    private JTextArea textArea;

    public JPanel getRootView() {
        return rootView;
    }

    public JRadioButton getRadioGson() {
        return radioGson;
    }

    public JRadioButton getRadioLogan() {
        return radioLogan;
    }

    public JButton getGenerateButton() {
        return generateButton;
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
