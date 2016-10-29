package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import testutils.JsonReader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by vadim on 02.10.16.
 */

public class ClassProcessorTest {
    private JsonReader jsonReader = new JsonReader();

    @InjectMocks
    ClassProcessor classProcessor;
    @Mock
    ClassGenerateHelper classGenerateHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSingleObjectGeneration_isCorrect() throws Exception {
        final JSONObject jsonObject = jsonReader.read("single_object.json");
        final String name = "Response";
        final Set<ClassItem> classItemSet = new HashSet<>();

        when(classGenerateHelper.getClassName(name))
                .thenReturn(name);

        classProcessor.proceed(jsonObject, name, classItemSet);
        assertTrue(classItemSet.size() == 1);

        Iterator iterator = classItemSet.iterator();
        ClassItem classItem = (ClassItem) iterator.next();
        assertEquals(classItem.getClassName(), name);

        final Map<String, ClassDecorator> fields = classItem.getClassFields();
        assertNotNull(fields);

        for (String key : jsonObject.keySet()) {
            assertTrue(fields.containsKey(key));
        }
    }

    @Test
    public void testInnerObjectGeneration_isCorrect() throws Exception {
        final JSONObject jsonObject = jsonReader.read("inner_json_object.json");
        final JSONObject innerJsonObject = jsonObject.optJSONObject("data");
        final String name = "Response";
        final Set<ClassItem> classItemSet = new HashSet<>();

        when(classGenerateHelper.getClassName(name))
                .thenReturn(name);

        classProcessor.proceed(jsonObject, name, classItemSet);
        assertTrue(classItemSet.size() == 2);

        for (ClassItem classItem : classItemSet) {
            final Map<String, ClassDecorator> fields = classItem.getClassFields();
            assertNotNull(fields);

            if (name.equalsIgnoreCase(classItem.getClassName())) {
                for (String key : jsonObject.keySet()) {
                    assertTrue(fields.containsKey(key));
                }
            } else {
                for (String key : innerJsonObject.keySet()) {
                    assertTrue(fields.containsKey(key));
                }
            }
        }
    }

    @Test
    public void testEmptyArrayGeneration_isCorrect() throws Exception {
        final JSONObject jsonObject = jsonReader.read("empty_array.json");
        final String name = "Response";
        final Set<ClassItem> classItemSet = new HashSet<>();

        when(classGenerateHelper.getClassName(name))
                .thenReturn(name);

        classProcessor.proceed(jsonObject, name, classItemSet);
        assertTrue(classItemSet.size() == 1);

        Iterator iterator = classItemSet.iterator();
        ClassItem classItem = (ClassItem) iterator.next();
        assertEquals(classItem.getClassName(), name);

        final Map<String, ClassDecorator> fields = classItem.getClassFields();
        assertNotNull(fields);

        for (String key : jsonObject.keySet()) {
            assertTrue(fields.containsKey(key));
        }

        final ClassDecorator targetObjectType = classItem.getClassFields().get("data");

        assertEquals("List<Object>", targetObjectType.getJavaItem());
    }

    @Test
    public void testIntegerArrayGeneration_isCorrect() throws Exception {
        final JSONObject jsonObject = jsonReader.read("array_with_primitive.json");
        final String name = "Response";
        final String targetType = "List<Integer>";

        final Set<ClassItem> classItemSet = new HashSet<>();

        when(classGenerateHelper.getClassName(name))
                .thenReturn(name);

        classProcessor.proceed(jsonObject, name, classItemSet);
        assertTrue(classItemSet.size() == 1);

        Iterator iterator = classItemSet.iterator();
        ClassItem classItem = (ClassItem) iterator.next();
        assertEquals(classItem.getClassName(), name);

        final Map<String, ClassDecorator> fields = classItem.getClassFields();
        assertNotNull(fields);

        for (String key : jsonObject.keySet()) {
            assertTrue(fields.containsKey(key));
        }

        final ClassDecorator actualType = classItem.getClassFields().get("data");

        assertEquals(targetType, actualType.getJavaItem());
    }

    @Test
    public void testInnerArrayObjectGeneration_isCorrect() throws Exception {
        final JSONObject jsonObject = jsonReader.read("array_with_jsonobject.json");
        final JSONArray innerJsonArray = jsonObject.optJSONArray("data");
        final JSONObject innerJsonObject = innerJsonArray.getJSONObject(0);
        final String name = "Response";
        final String targetType = "List<DataItem>";

        final Set<ClassItem> classItemSet = new HashSet<>();

        when(classGenerateHelper.getClassName(name))
                .thenReturn(name);
        when(classGenerateHelper.getClassNameWithItemPostfix(Mockito.anyString()))
                .thenReturn("DataItem");

        classProcessor.proceed(jsonObject, name, classItemSet);
        assertTrue(classItemSet.size() == 2);

        for (ClassItem classItem : classItemSet) {
            final Map<String, ClassDecorator> fields = classItem.getClassFields();
            assertNotNull(fields);

            if (name.equalsIgnoreCase(classItem.getClassName())) {
                for (String key : jsonObject.keySet()) {
                    assertTrue(fields.containsKey(key));
                    final ClassDecorator actualType = classItem.getClassFields().get("data");
                    final String javaItem = actualType.getJavaItem();
                    assertEquals(targetType, javaItem);
                }
            } else {
                for (String key : innerJsonObject.keySet()) {
                    assertTrue(fields.containsKey(key));
                }
            }
        }
    }
}
