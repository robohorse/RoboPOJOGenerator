package com.robohorse.robopojogenerator.form

import com.intellij.ui.components.JBScrollPane
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
import org.fife.ui.rsyntaxtextarea.SyntaxConstants
import org.fife.ui.rsyntaxtextarea.Theme
import javax.swing.ButtonGroup
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.JScrollPane
import javax.swing.JTextField

class GeneratorVew {
    lateinit var rootView: JPanel
    lateinit var generateButton: JButton
    lateinit var textArea: RSyntaxTextArea
    lateinit var rewriteExistingClassesCheckBox: JCheckBox
    lateinit var className: JTextField
    lateinit var jsonAreaScrollView: JScrollPane
    lateinit var JSONRadioButton: JRadioButton
    lateinit var JSONSchemaRadioButton: JRadioButton
    lateinit var javaRadioButton: JRadioButton
    lateinit var kotlinRadioButton: JRadioButton
    lateinit var sourcePanel: JPanel
    lateinit var languagePanel: JPanel
    lateinit var frameworkPanel: JPanel
    lateinit var frameworkList: JList<String>
    lateinit var actionPanel: JPanel
    lateinit var propertiesPanel: JPanel
    lateinit var commonInfoPanel: JPanel
    lateinit var controlsPanel: JPanel
    lateinit var scrollPropertiesPanel: JScrollPane
    lateinit var useTabsIndentation: JCheckBox
    lateinit var sourceGroup: ButtonGroup
    lateinit var languageGroup: ButtonGroup

    private fun createUIComponents() {
        textArea = RSyntaxTextArea().apply {
            syntaxEditingStyle = SyntaxConstants.SYNTAX_STYLE_JSON
            isCodeFoldingEnabled = true
            applyTheme(this)
        }
        jsonAreaScrollView = JBScrollPane(textArea)
    }

    private fun applyTheme(textArea: RSyntaxTextArea) = runCatching {
        Theme.load(javaClass.getResourceAsStream(THEME)).apply(textArea)
    }.onFailure {
        it.printStackTrace()
    }
}

private const val THEME = "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"
