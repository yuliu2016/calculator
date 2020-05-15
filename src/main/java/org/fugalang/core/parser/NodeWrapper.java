package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;

import java.util.*;
import java.util.function.Function;

public abstract class NodeWrapper {

    private final ParseTreeNode node;
    private Map<Integer, List<?>> list_cache;

    public NodeWrapper(ParserRule rule, ParseTreeNode node) {
        // requires that the node matches the correct rule
        node.failIfAbsent(rule);
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

    protected boolean has(int index, ParserRule rule) {
        var e = node.getItem(index);
        return e.isPresent(rule);
    }

    protected boolean has(int index, ElementType type) {
        var e = node.getItem(index);
        return e.isPresent(type);
    }

    protected boolean is(int index) {
        var e = node.getItem(index);
        return e.asBoolean();
    }

    protected <T> List<T> getList(int index, Function<ParseTreeNode, T> converter) {
        if (list_cache != null && list_cache.containsKey(index)) {
            return uncheckedTypeCast(list_cache.get(index));
        }
        List<T> resultOrNull = null;
        var e = node.getItem(index);
        for (var node : e.asCollection()) {
            if (resultOrNull == null) {
                resultOrNull = new ArrayList<>();
            }
            resultOrNull.add(converter.apply(node));
        }

        List<T> result = resultOrNull == null ? Collections.emptyList() : resultOrNull;
        if (list_cache == null) {
            list_cache = new HashMap<>();
        }
        list_cache.put(index, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> uncheckedTypeCast(List<?> list) {
        return (List<T>) list;
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
