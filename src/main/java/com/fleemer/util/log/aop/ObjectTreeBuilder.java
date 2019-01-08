package com.fleemer.util.log.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectTreeBuilder {
    private static final String ELEMENT_NAME_TEMPLATE = "%s[%s]";
    private static final String ENTRY_KEY_NAME = "key";
    private static final String ENTRY_VALUE_NAME = "value";
    private static final String ILLEGAL_ACCESS_EXCEPTION_MSG = "An error occurred while getting the value of the field {}. A null value will " +
            "be returned.{}";
    private static final String ILLEGAL_PRIMITIVE_ARRAY_TYPE_ERROR = "An array is not an array of primitive types. Object type will be used.";
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTreeBuilder.class);
    private static final String NEW_LINE = System.lineSeparator();

    private boolean showNull;

    public ObjectTreeBuilder showNull() {
        this.showNull = true;
        return this;
    }

    public Node asTree(Object o) {
        return asTree(null, o, (String) null);
    }

    public String asString(Object o) {
        Node tree = asTree(o);
        if (tree == null) {
            return null;
        }
        return buildString(new StringBuilder(), tree, StringUtils.EMPTY).toString();
    }

    private Node asTree(Node parent, byte o, String name) {
        return new Node(parent, byte.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, short o, String name) {
        return new Node(parent, short.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, int o, String name) {
        return new Node(parent, int.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, long o, String name) {
        return new Node(parent, long.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, float o, String name) {
        return new Node(parent, float.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, double o, String name) {
        return new Node(parent, double.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, char o, String name) {
        return new Node(parent, char.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, boolean o, String name) {
        return new Node(parent, boolean.class, normalizedName(name), o, null, false);
    }

    private Node asTree(Node parent, Object o, String name) {
        if (o == null) {
            if (showNull) {
                return new Node(parent, null, normalizedName(name), null, null, false);
            }
            return null;
        }
        Class<?> type = o.getClass();
        Node result = new Node(parent, type, normalizedName(name), o, null, false);
        if (isPrimitiveWrapper(type) || isString(type)) {
            return result;
        }
        Node cyclicNode = checkOnCyclicLink(parent, o);
        if (cyclicNode != null) {
            result.setChildren(cyclicNode.getChildren());
            result.setCyclicLink(true);
            return result;
        }
        assert type != null;
        if (type.isArray()) {
            if (type.getComponentType().isPrimitive()) {
                List<Object> elements = primitivesArrayToList(o);
                return buildPrimitivesArrayTree(result, elements);
            }
            List<?> elements = Arrays.asList((Object[]) o);
            return buildCollectionTree(result, elements);
        }
        if (isCollection(type)) {
            List<?> elements = new ArrayList<>((Collection<?>) o);
            return buildCollectionTree(result, elements);
        }
        if (isMap(type)) {
            List<?> entries = new ArrayList<>(((Map<?, ?>) o).entrySet());
            return buildCollectionTree(result, entries);
        }
        if (isMapEntry(type)) {
            Entry<?, ?> entry = (Entry<?, ?>) o;
            List<Node> children = new ArrayList<>();
            children.add(asTree(result, entry.getKey(), ENTRY_KEY_NAME));
            children.add(asTree(result, entry.getValue(), ENTRY_VALUE_NAME));
            result.setChildren(children);
            return result;
        }
        List<Node> children = FieldUtils.getAllFieldsList(type)
                .stream()
                .map(field -> asTree(result, o, field))
                .collect(Collectors.toList());
        result.setChildren(children);
        return result;
    }

    private Node asTree(Node parent, Object o, Field field) {
        if (field == null) {
            return asTree(parent, null, (String) null);
        }
        field.setAccessible(true);
        String name = field.getName();
        Object value = getValue(o, field);
        Class<?> type = field.getType();
        if (value != null && type.isPrimitive()) {
            return buildPrimitiveNode(parent, value, name, type);
        }
        Node result = asTree(parent, value, name);
        if (result != null && result.getType() == null) {
            result.setType(type);
        }
        return result;
    }

    private Node buildPrimitiveNode(Node parent, Object o, String name, Class<?> type) {
        if (o == null) {
            return asTree(parent, null, name);
        }
        if (isPrimitiveByte(type)) {
            return asTree(parent, ((Number) o).byteValue(), name);
        }
        if (isPrimitiveShort(type)) {
            return asTree(parent, ((Number) o).shortValue(), name);
        }
        if (isPrimitiveInt(type)) {
            return asTree(parent, ((Number) o).intValue(), name);
        }
        if (isPrimitiveLong(type)) {
            return asTree(parent, ((Number) o).longValue(), name);
        }
        if (isPrimitiveFloat(type)) {
            return asTree(parent, ((Number) o).floatValue(), name);
        }
        if (isPrimitiveDouble(type)) {
            return asTree(parent, ((Number) o).doubleValue(), name);
        }
        if (isPrimitiveChar(type)) {
            return asTree(parent, ((Character) o).charValue(), name);
        }
        if (isPrimitiveBoolean(type)) {
            return asTree(parent, ((Boolean) o).booleanValue(), name);
        }
        LOGGER.error(ILLEGAL_PRIMITIVE_ARRAY_TYPE_ERROR);
        return asTree(parent, o, name);
    }

    private Node buildPrimitivesArrayTree(Node node, List<?> elements) {
        if (node == null || elements == null) {
            return node;
        }
        Class<?> type = node.getType();
        if (type == null || !type.isArray()) {
            return node;
        }
        Class<?> elementType = type.getComponentType();
        String arrayName = node.getName();
        List<Node> children = IntStream.range(0, elements.size())
                .mapToObj(i -> buildPrimitiveNode(node, elements.get(i), String.format(ELEMENT_NAME_TEMPLATE, arrayName, i), elementType))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    private Node buildCollectionTree(Node node, List<?> elements) {
        if (node == null || elements == null) {
            return node;
        }
        String collectionName = node.getName();
        Class<?> type = node.getType();
        boolean hasIndexes = type != null && (isList(type) || type.isArray());
        List<Node> children = IntStream.range(0, elements.size())
                .mapToObj(i -> asTree(node, elements.get(i), String.format(ELEMENT_NAME_TEMPLATE, collectionName, hasIndexes ? i : "*")))
                .collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    private static String normalizedName(String name) {
        return name != null ? name : StringUtils.EMPTY;
    }

    private static Node checkOnCyclicLink(Node parent, Object o) {
        if (parent == null || o == null) {
            return null;
        }
        do {
            if (o == parent.getValue()) {
                return parent;
            }
        } while ((parent = parent.getParent()) != null);
        return null;
    }

    private static List<Object> primitivesArrayToList(Object arrayOfPrimitives) {
        List<Object> elements = new ArrayList<>();
        if (arrayOfPrimitives == null) {
            return elements;
        }
        int index = 0;
        while (index >= 0) {
            try {
                elements.add(Array.get(arrayOfPrimitives, index++));
            } catch (ArrayIndexOutOfBoundsException e) {
                index = -1;
            }
        }
        return elements;
    }

    private static Object getValue(Object o, Field field) {
        if (o == null || field == null) {
            return null;
        }
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            LOGGER.error(ILLEGAL_ACCESS_EXCEPTION_MSG, field, NEW_LINE, e);
        }
        return null;
    }

    private static boolean isPrimitiveBoolean(Class<?> type) {
        return type != null && boolean.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveChar(Class<?> type) {
        return type != null && char.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveDouble(Class<?> type) {
        return type != null && double.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveFloat(Class<?> type) {
        return type != null && float.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveLong(Class<?> type) {
        return type != null && long.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveInt(Class<?> type) {
        return type != null && int.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveShort(Class<?> type) {
        return type != null && short.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveByte(Class<?> type) {
        return type != null && byte.class.isAssignableFrom(type);
    }

    private static boolean isMapEntry(Class<?> type) {
        return type != null && Entry.class.isAssignableFrom(type);
    }

    private static boolean isMap(Class<?> type) {
        return type != null && Map.class.isAssignableFrom(type);
    }

    private static boolean isList(Class<?> type) {
        return type != null && List.class.isAssignableFrom(type);
    }

    private static boolean isCollection(Class<?> type) {
        return type != null && Collection.class.isAssignableFrom(type);
    }

    private static boolean isString(Class<?> type) {
        return type != null && String.class.isAssignableFrom(type);
    }

    private static boolean isPrimitiveWrapper(Class<?> type) {
        if (type == null) {
            return false;
        }
        return Number.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type);
    }

    private static StringBuilder buildString(StringBuilder sb, Node node, String prefix) {
        if (node == null || sb == null || prefix == null) {
            return sb;
        }
        appendIfLastCharNotEqualsTo(sb, '\n', NEW_LINE);
        sb.append(prefix);
        appendIfLastCharNotEqualsTo(sb, '|', "`");
        sb.append('-');
        appendType(sb, node);
        sb.append(' ').append('\'').append(node.getName()).append('\'');
        if (node.isCyclicLink()) {
            appendLink(sb, node);
            return sb;
        }
        List<Node> children = node.getChildren();
        if (children == null || children.isEmpty()) {
            return sb.append(": ").append(node.getValue());
        }
        int childrenSize = children.size();
        IntStream.range(0, childrenSize)
                .forEach(i -> {
                    String newPrefix = prefix + '\t' + (i < childrenSize - 1 ? '|' : StringUtils.EMPTY);
                    buildString(sb, children.get(i), newPrefix);
                });
        appendNewLineAfterLastChild(sb, prefix, children);
        return sb;
    }

    private static void appendNewLineAfterLastChild(StringBuilder sb, String prefix, List<Node> children) {
        if (sb == null || children == null || children.isEmpty()) {
            return;
        }
        Node lastChild = children.get(children.size() - 1);
        if (lastChild == null) {
            return;
        }
        List<Node> grandchildren = lastChild.getChildren();
        if (grandchildren == null || grandchildren.isEmpty() || lastChild.isCyclicLink()) {
            sb.append(NEW_LINE).append(prefix);
        }
    }

    private static void appendType(StringBuilder sb, Node node) {
        if (sb == null || node == null) {
            return;
        }
        Class<?> type = node.getType();
        if (type != null) {
            sb.append(' ').append(type.getSimpleName());
        }
    }

    private static void appendIfLastCharNotEqualsTo(StringBuilder sb, char lookFor, String appendingText) {
        if (sb == null) {
            return;
        }
        int length = sb.length();
        if (length != 0 && sb.charAt(length - 1) != lookFor) {
            sb.append(appendingText);
        }
    }

    private static void appendLink(StringBuilder sb, Node node) {
        if (sb == null || node == null) {
            return;
        }
        Node ancestor = node.getParent();
        sb.append(" link to ");
        while (ancestor != null) {
            sb.append("../");
            if (ancestor.getValue() == node.getValue()) {
                sb.append(ancestor.getName());
                break;
            }
            ancestor = ancestor.getParent();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Node {
        private Node parent;
        private Class<?> type;
        private String name;
        private Object value;
        private List<Node> children;
        private boolean cyclicLink;

        @Override
        public String toString() {
            return NEW_LINE +
                    "Node{" +
                    "parent=" + (parent != null ? parent.name : null) +
                    ", type=" + type +
                    ", name=\'" + name +
                    "\', value=" + (cyclicLink ? "link to \'" + name + '\'' : value) +
                    ", children=" + children +
                    ", cyclicLink=" + cyclicLink +
                    '}';
        }
    }
}
