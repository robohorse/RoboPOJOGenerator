package com.robohorse.robopojogenerator.view

import java.awt.event.ItemEvent
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JCheckBox

class GeneratorViewBinder(
        propertiesFactory: PropertiesFactory
) {
    val properties = propertiesFactory.createControls()

    fun bindView(
            generatorVew: GeneratorVew
    ) {
        bindSource(generatorVew)
        bindLanguage(generatorVew)
        bindFrameworksAndProperties(generatorVew)
    }

    private fun bindFrameworksAndProperties(generatorVew: GeneratorVew) {
        bindFrameworks(generatorVew)
        bindAdditionalProperties(generatorVew)
    }

    private fun bindAdditionalProperties(generatorVew: GeneratorVew) {
        generatorVew.propertiesPanel?.apply {
            removeAll()
            layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
            with(properties.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.properties?.let { properties ->
                    properties.forEach { itemProperty ->
                        add(JCheckBox(itemProperty.propertyName).apply {
                            isSelected = itemProperty.selected
                            addItemListener { itemEvent ->
                                properties.firstOrNull {
                                    it.propertyName == text
                                }?.let { targetProperty ->
                                    targetProperty.selected = itemEvent.stateChange == ItemEvent.SELECTED
                                }
                            }
                        })
                    }
                }
            }
            revalidate()
            repaint()
        }
    }

    private fun bindSource(generatorVew: GeneratorVew) {
        with(generatorVew) {
            jsonRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON)
                }
            }
            jsonSchemaRadioButton.addItemListener { event ->
                if (event.stateChange == ItemEvent.SELECTED) {
                    resolveSource(SourceVM.JSON_SCHEMA)
                }
            }
            //TODO: disable when Json Schema support will be added
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
            selectedSource = sources.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun resolveLanguage(key: String) {
        with(properties.selectedSource) {
            this?.selectedLanguage = this?.languages?.firstOrNull {
                it.propertyName == key
            }
        }
    }

    private fun bindFrameworks(generatorVew: GeneratorVew) {
        generatorVew.list1.apply {
            removeAll()
            setListData(Vector<String>().apply {
                properties.selectedSource?.selectedLanguage?.frameworks?.let { list ->
                    addAll(list.map { it.propertyName })
                }
            })
            selectedIndex = with(properties.selectedSource?.selectedLanguage) {
                this?.selectedFramework?.let {
                    this.frameworks.indexOf(selectedFramework as FrameworkVW)
                } ?: 0
            }
            addListSelectionListener { selectionEvent ->
                if (!selectionEvent.valueIsAdjusting) {
                    with(properties.selectedSource?.selectedLanguage) {
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
