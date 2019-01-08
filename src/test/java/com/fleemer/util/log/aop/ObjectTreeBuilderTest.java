package com.fleemer.util.log.aop;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Every.everyItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.fleemer.util.log.aop.ObjectTreeBuilder.Node;
import com.google.common.collect.Iterables;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class ObjectTreeBuilderTest {
    private ObjectTreeBuilder objectTreeBuilder;

    @Before
    public void setUp() {
        objectTreeBuilder = new ObjectTreeBuilder();
    }

    @Test
    public void showNull() throws NoSuchFieldException, IllegalAccessException {
        Field field = objectTreeBuilder.getClass().getDeclaredField("showNull");
        field.setAccessible(true);
        assertFalse((Boolean) field.get(objectTreeBuilder));
        objectTreeBuilder.showNull();
        assertTrue((Boolean) field.get(objectTreeBuilder));
    }

    @Test
    public void isPrimitiveBoolean() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveBoolean", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Boolean.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, boolean.class));
    }

    @Test
    public void isPrimitiveChar() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveChar", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Character.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, char.class));
    }

    @Test
    public void isPrimitiveDouble() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveDouble", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Double.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, double.class));
    }

    @Test
    public void isPrimitiveFloat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveFloat", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Float.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, float.class));
    }

    @Test
    public void isPrimitiveLong() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveLong", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Long.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, long.class));
    }

    @Test
    public void isPrimitiveInt() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveInt", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Integer.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, int.class));
    }

    @Test
    public void isPrimitiveShort() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveShort", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Short.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, short.class));
    }

    @Test
    public void isPrimitiveByte() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveByte", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Byte.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, byte.class));
    }

    @Test
    public void isMapEntry() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isMapEntry", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, List.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Entry.class));
    }

    @Test
    public void isMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isMap", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, List.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Entry.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Map.class));
    }

    @Test
    public void isList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isList", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, List.class));
    }

    @Test
    public void isCollection() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isCollection", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, String.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Collection.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, List.class));
    }

    @Test
    public void isString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isString", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Map.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Character.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, String.class));
    }

    @Test
    public void isPrimitiveWrapper() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("isPrimitiveWrapper", Class.class);
        method.setAccessible(true);
        assertFalse((Boolean) method.invoke(objectTreeBuilder, (Class) null));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, Object.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, List.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, byte.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, short.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, int.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, long.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, float.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, double.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, boolean.class));
        assertFalse((Boolean) method.invoke(objectTreeBuilder, char.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Byte.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Short.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Integer.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Long.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Float.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Double.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Number.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Character.class));
        assertTrue((Boolean) method.invoke(objectTreeBuilder, Boolean.class));
    }

    @Test
    public void getValue_exception() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("getValue", Object.class, Field.class);
        method.setAccessible(true);
        MyClass arg = new MyClass();
        Field field = MyClass.class.getDeclaredField("i");
        assertNull(method.invoke(objectTreeBuilder, arg, field));
    }

    @Test
    public void getValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("getValue", Object.class, Field.class);
        method.setAccessible(true);
        MyClass arg = new MyClass();
        Field field = MyClass.class.getDeclaredField("i");
        field.setAccessible(true);
        assertNull(method.invoke(objectTreeBuilder, null, null));
        assertNull(method.invoke(objectTreeBuilder, null, field));
        assertNull(method.invoke(objectTreeBuilder, arg, null));
        assertEquals(11, method.invoke(objectTreeBuilder, arg, field));
    }

    @Test
    public void primitivesArrayToList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("primitivesArrayToList", Object.class);
        method.setAccessible(true);
        int[] arg = {6, 0, -2};
        assertTrue(((List) method.invoke(objectTreeBuilder, (Object) null)).isEmpty());
        List<Integer> expected = Arrays.stream(arg).boxed().collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(expected, (Iterable<?>) method.invoke(objectTreeBuilder, (Object) arg)));
    }

    @Test
    public void checkOnCyclicLink() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("checkOnCyclicLink", Node.class, Object.class);
        method.setAccessible(true);
        Object o = new Object();
        Node parent = new Node(null, Object.class, "parent", new Object(), null, false);
        Node child = new Node(parent, Object.class, "child", new Object(), null, false);
        assertNull(method.invoke(objectTreeBuilder, null, null));
        assertNull(method.invoke(objectTreeBuilder, null, o));
        assertNull(method.invoke(objectTreeBuilder, child, null));
        assertNull(method.invoke(objectTreeBuilder, child, o));
        child.setValue(o);
        parent.setValue(o);
        assertEquals(child, method.invoke(objectTreeBuilder, child, o));
        child.setValue(new Object());
        assertEquals(parent, method.invoke(objectTreeBuilder, child, o));
    }

    @Test
    public void normalizedName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("normalizedName", String.class);
        method.setAccessible(true);
        assertEquals(StringUtils.EMPTY, method.invoke(objectTreeBuilder, (String) null));
        assertEquals(StringUtils.EMPTY, method.invoke(objectTreeBuilder, StringUtils.EMPTY));
        assertEquals("anyString", method.invoke(objectTreeBuilder, "anyString"));
        assertEquals("any string", method.invoke(objectTreeBuilder, "any string"));
    }

    @Test
    public void buildCollectionTree() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("buildCollectionTree", Node.class, List.class);
        method.setAccessible(true);
        List<Integer> arg = Arrays.asList(45, -3, 0, 2);
        Node node = new Node(null, arg.getClass(), "list", arg, null, false);
        assertNull(method.invoke(objectTreeBuilder, null, null));
        assertNull(method.invoke(objectTreeBuilder, null, arg));

        Object resultWithNullArg = method.invoke(objectTreeBuilder, node, null);
        assertEquals(node, resultWithNullArg);
        assertNull(((Node) resultWithNullArg).getChildren());

        Object result = method.invoke(objectTreeBuilder, node, arg);
        assertEquals(node, result);
        List<Object> actualValues = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(arg, actualValues));
        List<String> expectedNames = Arrays.asList("list[0]", "list[1]", "list[2]", "list[3]");
        List<Object> actualNames = ((Node) result).getChildren().stream().map(Node::getName).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(expectedNames, actualNames));

        node.setType(Object[].class);
        Object result1 = method.invoke(objectTreeBuilder, node, arg);
        assertEquals(node, result1);
        List<Object> actualValues1 = ((Node) result1).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(arg, actualValues1));
        List<String> expectedNames1 = Arrays.asList("list[0]", "list[1]", "list[2]", "list[3]");
        List<Object> actualNames1 = ((Node) result1).getChildren().stream().map(Node::getName).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(expectedNames1, actualNames1));

        node.setType(Set.class);
        Object result2 = method.invoke(objectTreeBuilder, node, arg);
        assertEquals(node, result2);
        List<Object> actualValues2 = ((Node) result2).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(arg, actualValues2));
        List<String> expectedNames2 = Arrays.asList("list[*]", "list[*]", "list[*]", "list[*]");
        List<Object> actualNames2 = ((Node) result2).getChildren().stream().map(Node::getName).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(expectedNames2, actualNames2));
    }

    @Test
    public void buildPrimitivesArrayTree() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("buildPrimitivesArrayTree", Node.class, List.class);
        method.setAccessible(true);
        List<Integer> arg = Arrays.asList(45, -3, 0, 2);
        Node node = new Node(null, null, "array", arg, null, false);
        assertNull(method.invoke(objectTreeBuilder, null, null));
        assertNull(method.invoke(objectTreeBuilder, null, arg));
        assertEquals(node, method.invoke(objectTreeBuilder, node, null));
        assertEquals(node, method.invoke(objectTreeBuilder, node, arg));

        node.setType(List.class);
        assertEquals(node, method.invoke(objectTreeBuilder, node, arg));

        node.setType(int[].class);
        Object result = method.invoke(objectTreeBuilder, node, arg);
        assertEquals(node, result);
        List<Object> actualValues = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(arg, actualValues));
        List<String> expectedNames = Arrays.asList("array[0]", "array[1]", "array[2]", "array[3]");
        List<Object> actualNames = ((Node) result).getChildren().stream().map(Node::getName).collect(Collectors.toList());
        assertTrue(Iterables.elementsEqual(expectedNames, actualNames));
    }

    @Test
    public void buildPrimitiveNode() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("buildPrimitiveNode", Node.class, Object.class, String.class, Class.class);
        method.setAccessible(true);
        Node parent = new Node(null, Object.class, "myClass", new Object(), null, false);
        assertNull(method.invoke(objectTreeBuilder, null, null, null, null));
        assertNull(method.invoke(objectTreeBuilder, parent, null, null, null));
        assertNull(method.invoke(objectTreeBuilder, parent, null, "name", null));
        assertNull(method.invoke(objectTreeBuilder, parent, null, null, int.class));
        assertNull(method.invoke(objectTreeBuilder, parent, null, "name", int.class));
        Object byteResult = method.invoke(objectTreeBuilder, parent, new Byte((byte) 5), "name", byte.class);
        assertEquals((byte) 5, ((Node) byteResult).getValue());
        assertEquals(byte.class, ((Node) byteResult).getType());

        Object shortResult = method.invoke(objectTreeBuilder, parent, new Short((short) 5), "name", short.class);
        assertEquals((short) 5, ((Node) shortResult).getValue());
        assertEquals(short.class, ((Node) shortResult).getType());

        Object intResult = method.invoke(objectTreeBuilder, parent, new Integer(5), "name", int.class);
        assertEquals(5, ((Node) intResult).getValue());
        assertEquals(int.class, ((Node) intResult).getType());

        Object longResult = method.invoke(objectTreeBuilder, parent, new Long(5L), "name", long.class);
        assertEquals(5L, ((Node) longResult).getValue());
        assertEquals(long.class, ((Node) longResult).getType());

        Object floatResult = method.invoke(objectTreeBuilder, parent, new Float(5.2f), "name", float.class);
        assertEquals(5.2f, ((Node) floatResult).getValue());
        assertEquals(float.class, ((Node) floatResult).getType());

        Object doubleResult = method.invoke(objectTreeBuilder, parent, new Double(5.2), "name", double.class);
        assertEquals(5.2, ((Node) doubleResult).getValue());
        assertEquals(double.class, ((Node) doubleResult).getType());

        Object charResult = method.invoke(objectTreeBuilder, parent, new Character('i'), "name", char.class);
        assertEquals('i', ((Node) charResult).getValue());
        assertEquals(char.class, ((Node) charResult).getType());

        Object booleanResult = method.invoke(objectTreeBuilder, parent, new Boolean(true), "name", boolean.class);
        assertTrue((Boolean) ((Node) booleanResult).getValue());
        assertEquals(boolean.class, ((Node) booleanResult).getType());

        Object objectResult = method.invoke(objectTreeBuilder, parent, "any", "name", String.class);
        assertEquals("any", ((Node) objectResult).getValue());
        assertEquals(String.class, ((Node) objectResult).getType());
    }

    @Test
    public void asTree_byte() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, byte.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, (byte) 1, "name");
        assertEquals(byte.class, ((Node) result).getType());
        assertEquals((byte) 1, ((Node) result).getValue());
    }

    @Test
    public void asTree_short() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, short.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, (short) 1, "name");
        assertEquals(short.class, ((Node) result).getType());
        assertEquals((short) 1, ((Node) result).getValue());
    }

    @Test
    public void asTree_int() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, int.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, 1, "name");
        assertEquals(int.class, ((Node) result).getType());
        assertEquals(1, ((Node) result).getValue());
    }

    @Test
    public void asTree_long() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, long.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, 1L, "name");
        assertEquals(long.class, ((Node) result).getType());
        assertEquals(1L, ((Node) result).getValue());
    }

    @Test
    public void asTree_float() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, float.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, 1.5f, "name");
        assertEquals(float.class, ((Node) result).getType());
        assertEquals(1.5f, ((Node) result).getValue());
    }

    @Test
    public void asTree_double() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, double.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, 1.5, "name");
        assertEquals(double.class, ((Node) result).getType());
        assertEquals(1.5, ((Node) result).getValue());
    }

    @Test
    public void asTree_char() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, char.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, 'i', "name");
        assertEquals(char.class, ((Node) result).getType());
        assertEquals('i', ((Node) result).getValue());
    }

    @Test
    public void asTree_boolean() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, boolean.class, String.class);
        method.setAccessible(true);
        Object result = method.invoke(objectTreeBuilder, null, true, "name");
        assertEquals(boolean.class, ((Node) result).getType());
        assertTrue((Boolean) ((Node) result).getValue());
    }

    @Test
    public void asTree_object() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, Object.class, String.class);
        method.setAccessible(true);
        Field field = objectTreeBuilder.getClass().getDeclaredField("showNull");
        field.setAccessible(true);
        field.set(objectTreeBuilder, false);
        assertNull(method.invoke(objectTreeBuilder, null, null, null));
        field.set(objectTreeBuilder, true);
        Object result = method.invoke(objectTreeBuilder, null, null, "name");
        assertNull(((Node) result).getType());
        assertEquals("name", ((Node) result).getName());

        assertEquals((byte) 10, ((Node) method.invoke(objectTreeBuilder, null, new Byte((byte) 10), "name")).getValue());
        assertEquals((short) 10, ((Node) method.invoke(objectTreeBuilder, null, new Short((short) 10), "name")).getValue());
        assertEquals(10, ((Node) method.invoke(objectTreeBuilder, null, new Integer(10), "name")).getValue());
        assertEquals(10L, ((Node) method.invoke(objectTreeBuilder, null, new Long(10L), "name")).getValue());
        assertEquals(10.1f, ((Node) method.invoke(objectTreeBuilder, null, new Float(10.1f), "name")).getValue());
        assertEquals(10.1, ((Node) method.invoke(objectTreeBuilder, null, new Double(10.1), "name")).getValue());
        assertEquals('i', ((Node) method.invoke(objectTreeBuilder, null, new Character('i'), "name")).getValue());
        assertEquals(true, ((Node) method.invoke(objectTreeBuilder, null, new Boolean(true), "name")).getValue());

        // Cyclic link
        Object o = new Object();
        Node parent = new Node(null, null, "name", o, null, false);
        assertTrue(((Node) method.invoke(objectTreeBuilder, parent, o, "name")).isCyclicLink());

        // Primitives' array
        int[] primitiveArray = {1, -1, 2, 0};
        result = method.invoke(objectTreeBuilder, null, primitiveArray, "name");
        Object[] actualArray = ((Node) result).getChildren().stream().map(Node::getValue).toArray();
        assertThat(actualArray, arrayContaining(1, -1, 2, 0));
        assertThat(((Node) result).getChildren(), everyItem(hasProperty("type", equalTo(int.class))));

        // Objects' array
        Integer[] objectArray = {1, -1, 2, 0};
        result = method.invoke(objectTreeBuilder, null, objectArray, "name");
        actualArray = ((Node) result).getChildren().stream().map(Node::getValue).toArray();
        assertThat(actualArray, arrayContaining(1, -1, 2, 0));
        assertThat(((Node) result).getChildren(), everyItem(hasProperty("type", equalTo(Integer.class))));

        // Collection
        List<Integer> list = Arrays.asList(1, -1, 2, 0);
        result = method.invoke(objectTreeBuilder, null, list, "name");
        List<?> actualList = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertThat(actualList, contains(1, -1, 2, 0));
        assertThat(((Node) result).getChildren(), everyItem(hasProperty("type", equalTo(Integer.class))));

        // Map
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        result = method.invoke(objectTreeBuilder, null, map, "name");
        actualList = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertThat(actualList, everyItem(instanceOf(Entry.class)));

        // Map entry
        map.remove("one");
        Entry<String, Integer> entry = new ArrayList<>(map.entrySet()).get(0);
        result = method.invoke(objectTreeBuilder, null, entry, "name");
        actualList = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertThat(actualList, contains("two", 2));

        // Object
        MyClass myClass = new MyClass();
        result = method.invoke(objectTreeBuilder, null, myClass, "name");
        actualList = ((Node) result).getChildren().stream().map(Node::getValue).collect(Collectors.toList());
        assertThat(actualList, contains(11));
    }

    @Test
    public void asTree_field() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("asTree", Node.class, Object.class, Field.class);
        method.setAccessible(true);
        assertNull(method.invoke(objectTreeBuilder, null, null, null));
        Node parent = new Node(null, null, null, null, null, false);
        assertNull(method.invoke(objectTreeBuilder, parent, null, null));
        MyClass myClass = new MyClass();
        assertNull(method.invoke(objectTreeBuilder, null, myClass, null));
        assertNull(method.invoke(objectTreeBuilder, parent, myClass, null));
        MyClass.class.getDeclaredField("i");
        assertNull(method.invoke(objectTreeBuilder, parent, myClass, null));
        objectTreeBuilder.showNull();
        assertNull(((Node) method.invoke(objectTreeBuilder, parent, myClass, null)).getType());
        Field field = myClass.getClass().getDeclaredField("i");
        assertEquals(int.class, ((Node) method.invoke(objectTreeBuilder, parent, myClass, field)).getType());
    }

    @Test
    public void appendNewLineAfterLastChild() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("appendNewLineAfterLastChild", StringBuilder.class, String.class, List.class);
        method.setAccessible(true);
        StringBuilder sb = new StringBuilder().append("any string");
        Node node = new Node(null, null, null, null, null, false);

        String expected = "any string";
        method.invoke(objectTreeBuilder, null, null, null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, null, Collections.singletonList(node));
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, "prefix", Collections.singletonList(node));
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, null, null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, "prefix", null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, "prefix", new ArrayList<>());
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, "prefix", Collections.singletonList(null));
        assertEquals(expected, sb.toString());

        expected += System.lineSeparator() + "prefix";
        method.invoke(objectTreeBuilder, sb, "prefix", Collections.singletonList(node));
        assertEquals(expected, sb.toString());

        node.setChildren(new ArrayList<>());
        expected += System.lineSeparator() + "prefix";
        method.invoke(objectTreeBuilder, sb, "prefix", Collections.singletonList(node));
        assertEquals(expected, sb.toString());

        node.setChildren(Arrays.asList(new Node(null, null, null, null, null, false), node));
        method.invoke(objectTreeBuilder, sb, "prefix", Collections.singletonList(node));
        assertEquals(expected, sb.toString());

        node.setCyclicLink(true);
        expected += System.lineSeparator() + "prefix";
        method.invoke(objectTreeBuilder, sb, "prefix", Collections.singletonList(node));
        assertEquals(expected, sb.toString());
    }

    @Test
    public void appendType() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("appendType", StringBuilder.class, Node.class);
        method.setAccessible(true);
        StringBuilder sb = new StringBuilder().append("any string");
        Node node = new Node(null, null, null, null, null, false);

        String expected = "any string";
        method.invoke(objectTreeBuilder, null, null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, node);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, node);
        assertEquals(expected, sb.toString());

        expected += " String";
        node.setType(String.class);
        method.invoke(objectTreeBuilder, sb, node);
        assertEquals(expected, sb.toString());
    }

    @Test
    public void appendIfLastCharNotEqualsTo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("appendIfLastCharNotEqualsTo", StringBuilder.class, char.class, String.class);
        method.setAccessible(true);
        StringBuilder sb = new StringBuilder();

        String expected = "";
        method.invoke(objectTreeBuilder, null, ' ', null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, 'i', null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, 'i', "add string");
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, 'i', "add string");
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, 'i', "add string");
        assertEquals(expected, sb.toString());

        sb.append("any string");
        expected += "any string";
        method.invoke(objectTreeBuilder, sb, 'g', "add string");
        assertEquals(expected, sb.toString());

        expected += "add string";
        method.invoke(objectTreeBuilder, sb, 'i', "add string");
        assertEquals(expected, sb.toString());
    }

    @Test
    public void appendLink() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = objectTreeBuilder.getClass().getDeclaredMethod("appendLink", StringBuilder.class, Node.class);
        method.setAccessible(true);
        StringBuilder sb = new StringBuilder().append("any string");
        Node node = new Node(null, null, null, null, null, false);

        String expected = "any string";
        method.invoke(objectTreeBuilder, null, null);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, null, node);
        assertEquals(expected, sb.toString());

        method.invoke(objectTreeBuilder, sb, null);
        assertEquals(expected, sb.toString());

        expected += " link to ";
        method.invoke(objectTreeBuilder, sb, node);
        assertEquals(expected, sb.toString());

        Object o = new Object();
        node.setValue(o);
        node.setParent(new Node(new Node(null, null, "grandparent", o, null, false), null, "parent", new Object(), null, false));
        expected += " link to ../../grandparent";
        method.invoke(objectTreeBuilder, sb, node);
        assertEquals(expected, sb.toString());
    }

    private static class MyClass {
        private int i = 11;
    }
}
