package com.robohorse.robopojogenerator.generator.processing

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.common.JsonModel.JsonItem
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.JsonReader
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.json.JSONObject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class ClassProcessorTest {
    private val jsonReader: JsonReader = JsonReader()

    @InjectMockKs
    lateinit var classProcessor: ClassProcessor

    @RelaxedMockK
    lateinit var classGenerateHelper: ClassGenerateHelper

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun testSingleObjectGeneration_isCorrect() {
        val jsonObject = jsonReader.read("single_object.json") as JSONObject
        val name = "Response"
        every { classGenerateHelper.formatClassName(name) }.returns(name)
        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 1)
        val iterator = classItemMap.values.iterator()
        val (className, _, _, _, fields) = iterator.next()
        assertEquals(className, name)
        assertNotNull(fields)
        for (key in jsonObject.keySet()) {
            assertTrue(fields.containsKey(key))
        }
    }

    @Test
    fun testInnerObjectGeneration_isCorrect() {
        val jsonObject = jsonReader.read("inner_json_object.json") as JSONObject
        val innerJsonObject = jsonObject.optJSONObject("data")
        val name = "Response"
        every { classGenerateHelper.formatClassName(name) }.returns(name)

        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 2)
        for ((className, _, _, _, fields) in classItemMap.values) {
            assertNotNull(fields)
            if (name.equals(className, ignoreCase = true)) {
                for (key in jsonObject.keySet()) {
                    assertTrue(fields.containsKey(key))
                }
            } else {
                for (key in innerJsonObject.keySet()) {
                    assertTrue(fields.containsKey(key))
                }
            }
        }
    }

    @Test
    fun testEmptyArrayGeneration_isCorrect() {
        val jsonObject = jsonReader.read("empty_array.json") as JSONObject
        val name = "Response"
        every { classGenerateHelper.formatClassName(name) }.returns(name)
        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 1)
        val iterator: Iterator<*> = classItemMap.values.iterator()
        val (className, _, _, _, fields) = iterator.next() as ClassItem
        assertEquals(className, name)
        assertNotNull(fields)
        for (key in jsonObject.keySet()) {
            assertTrue(fields.containsKey(key))
        }
        val targetObjectType = fields["data"]
        assertEquals("List<Object>", targetObjectType!!.getJavaItem())
    }

    @Test
    fun testIntegerArrayGeneration_isCorrect() {
        val jsonObject = jsonReader.read("array_with_primitive.json") as JSONObject
        val name = "Response"
        val targetType = "List<Integer>"
        every { classGenerateHelper.formatClassName(name) }.returns(name)
        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 1)
        val iterator: Iterator<*> = classItemMap.values.iterator()
        val (className, _, _, _, fields) = iterator.next() as ClassItem
        assertEquals(className, name)
        assertNotNull(fields)
        for (key in jsonObject.keySet()) {
            assertTrue(fields.containsKey(key))
        }
        val actualType = fields["data"]
        assertEquals(targetType, actualType!!.getJavaItem())
    }

    @Test
    fun testInnerArrayObjectGeneration_isCorrect() {
        val jsonObject = jsonReader.read("array_with_jsonobject.json") as JSONObject
        val innerJsonArray = jsonObject.optJSONArray("data")
        val innerJsonObject = innerJsonArray.getJSONObject(0)
        val name = "Response"
        val targetType = "List<DataItem>"
        every { classGenerateHelper.formatClassName(name) }.returns(name)
        every { classGenerateHelper.getClassNameWithItemPostfix(any()) }.returns("DataItem")
        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 2)
        for ((className, _, _, _, fields) in classItemMap.values) {
            assertNotNull(fields)
            if (name.equals(className, ignoreCase = true)) {
                for (key in jsonObject.keySet()) {
                    assertTrue(fields.containsKey(key))
                    val actualType = fields["data"]
                    val javaItem = actualType!!.getJavaItem()
                    assertEquals(targetType, javaItem)
                }
            } else {
                for (key in innerJsonObject.keySet()) {
                    assertTrue(fields.containsKey(key))
                }
            }
        }
    }

    @Test
    fun testNestedArrayElementsConsolidateObjectProperties() {
        val jsonObject = jsonReader.read("nested_array_elements.json") as JSONObject
        val name = "Response"
        val classItemMap: LinkedHashMap<String?, ClassItem> = LinkedHashMap()
        val jsonItem = JsonItem(name, jsonObject)
        classProcessor.proceed(jsonItem, classItemMap)
        assertTrue(classItemMap.size == 1)
        val classFields = classItemMap.values.firstOrNull()?.classFields
        assertNotNull(classFields)
        // when using "itemMap.putAll(innerItemsMap);" element "C" will be missing as newer instances of objects in
        // innerItemsMap of the same name/type overwrite existing map (itemMap) entries
        listOf("Outsides", "Outside", "Insides", "Inside", "A", "B", "C").listIterator().forEach {
            assertTrue(classFields.containsKey(it), "Missing element: $it")
        }
    }
}
