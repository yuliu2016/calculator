package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class NodeWrapper {

    private final ParseTreeNode node;

    private Object[] cache;


    public NodeWrapper(ParseTreeNode node) {
        this.node = node;
    }

    protected ParseTreeNode get(int index) {
        return node.getItem(index);
    }

    @SuppressWarnings("unchecked")
    protected <T extends NodeWrapper> T get(int index, Function<ParseTreeNode, T> converter) {
        if (cache != null && cache[index] != null) {
            return (T) cache[index];
        }
        var result = converter.apply(node.getItem(index));
        if (cache == null) {
            cache = new Object[node.sizeOfChildren()];
        }
        cache[index] = result;
        return result;
    }

    @SuppressWarnings("unchecked")
    protected <T extends NodeWrapper> T get(int index, Class<T> wrapperClass) {
        if (cache != null && cache[index] != null) {
            return (T) cache[index];
        }
        T result;
        try {
            result = wrapperClass
                    .getConstructor(ParseTreeNode.class)
                    .newInstance(node.getItem(index));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (cache == null) {
            cache = new Object[node.sizeOfChildren()];
        }
        cache[index] = result;
        return result;
    }

    protected String get(int index, ElementType type) {
        var e = node.getItem(index);
        e.failIfAbsent(type);
        return e.asString();
    }

    protected boolean has(int index) {
        var e = node.getItem(index);
        return e.isPresent();
    }

    protected boolean is(int index) {
        var e = node.getItem(index);
        return e.asBoolean();
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> getList(int index, Function<ParseTreeNode, T> converter) {
        if (cache != null && cache[index] != null) {
            return (List<T>) cache[index];
        }
        List<T> result;
        var e = node.getItem(index);

        if (e.sizeOfChildren() == 0) {
            result = Collections.emptyList();
        } else {
            result = new ArrayList<>();
            for (var node : e.asCollection()) {
                result.add(converter.apply(node));
            }
        }

        if (cache == null) {
            cache = new Object[node.sizeOfChildren()];
        }
        cache[index] = result;
        return result;
    }

    @SuppressWarnings("unchecked")
    protected <T extends NodeWrapper> List<T> getList(int index, Class<T> wrapperClass) {
        if (cache != null && cache[index] != null) {
            return (List<T>) cache[index];
        }
        List<T> result;
        var e = node.getItem(index);

        if (e.sizeOfChildren() == 0) {
            result = Collections.emptyList();
        } else {
            result = new ArrayList<>();
            try {
                Constructor<T> constructor = wrapperClass.getConstructor(ParseTreeNode.class);
                for (var node : e.asCollection()) {
                    result.add(constructor.newInstance(node));
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if (cache == null) {
            cache = new Object[node.sizeOfChildren()];
        }
        cache[index] = result;
        return result;
    }

    @Override
    public String toString() {
        return prettyFormat(0);
    }

    public String prettyFormat(int indent) {
        return ParseTreePPrint.format(node, indent);
    }

    public ParseTreeNode getNode() {
        return node;
    }
}
