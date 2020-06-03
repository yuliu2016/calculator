package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;

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

        var size = e.sizeOfChildren();
        if (size == 0) {
            result = Collections.emptyList();
        } else {
            result = new ArrayList<>(size);
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

    @Override
    public String toString() {
        return prettyFormat(-1);
    }

    public String prettyFormat(int indent) {
        return ParseTreePPrint.format(node, indent);
    }

    public ParseTreeNode getNode() {
        return node;
    }
}
