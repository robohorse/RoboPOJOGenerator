package com.robohorse.robopojogenerator.view

import com.robohorse.robopojogenerator.form.GeneratorVew
import com.robohorse.robopojogenerator.models.ControlsModel
import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.LanguageVM
import com.robohorse.robopojogenerator.models.SourceVM
import java.awt.event.ItemEvent
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JCheckBox

internal class GeneratorViewBinder(
    private val propertiesFactory: PropertiesFactory
) {
    var properties: ControlsModel? = null

    fun bindView(
        generatorVew: GeneratorVew
    ) {
        properties = propertiesFactory.createControls()
        bindSource(generatorVew)
        bindLanguage(generatorVew)
        bindFrameworksAndProperties(generatorVew)
    }

    private fun bindFrameworksAndProperties(generatorVew: GeneratorVew) {
        bindFrameworks(generatorVew)
        bindAdditionalProperties(generatorVew)
    }

    private fun bindAdditionalProperties(generatorVew: GeneratorVew) {
        generatorVew.propertiesPanel.apply {
            removeAll()
            layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
            with(properties?.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.properties?.let { properties ->
                    properties.forEach { itemProperty ->
                        add(
                            JCheckBox(itemProperty.propertyName).apply {
                                isSelected = itemProperty.selected
                                addItemListener { itemEvent ->
                                    properties.firstOrNull {
                                        it.propertyName == text
                                    }?.let { targetProperty ->
                                        targetProperty.selected = itemEvent.stateChange == ItemEvent.SELECTED
                                    }
                                }
                            }
                        )
                    }
                }
            }
            revalidate()
            repaint()
        }
    }

    private fun bindSource(generatorVew: GeneratorVew) {
        with(generatorVew) {
            JSONRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON)
                }
            }
            JSONSchemaRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON_SCHEMA)
                }
            }
            // TODO: disable when Json Schema support will be added
            sourcePanel.isVisible = false
        }
    }

    private fun bindLanguage(generatorVew: GeneratorVew) {
        with(generatorVew) {
            javaRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveLanguage(LanguageVM.JAVA)
                    bindFrameworksAndProperties(generatorVew)
                }
            }
            kotlinRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveLanguage(LanguageVM.KOTLIN)
                    bindFrameworksAndProperties(generatorVew)
                }
            }
        }
    }

    private fun resolveSource(key: String) {
        with(properties) {
            this?.selectedSource = this?.sources?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolveLanguage(key: String) {
        with(properties?.selectedSource) {
            this?.selectedLanguage = this?.languages?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun bindFrameworks(generatorVew: GeneratorVew) {
        generatorVew.frameworkList.apply {
            removeAll()
            setListData(
                Vector<String>().apply {
                    properties?.selectedSource?.selectedLanguage?.frameworks?.let { list ->
                        addAll(list.map { it.propertyName })
                    }
                }
            )
            selectedIndex = with(properties?.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.let {
                    this.frameworks.indexOf(selectedFramework as FrameworkVW)
                } ?: 0
            }
            addListSelectionListener { selectionEvent ->
                if (!selectionEvent.valueIsAdjusting) {
                    with(properties?.selectedSource?.selectedLanguage) {
                        this?.frameworks?.firstOrNull {
                            it.propertyName == selectedValue
                        }?.let {
                            selectedFramework = it
                            bindAdditionalProperties(generatorVew)
                        }
                    }
                }
            }
        }
    }
}
