package org.fugalang.core.parser;

import java.util.List;

class IndexNode implements ParseTreeNode {

    static final ParseTreeNode NULL = new IndexNode(null, false,
            false, null, null);

    private final List<ParseTreeNode> children;

    private final boolean isPresent;
    private final boolean isCollection;
    private final ParserRule rule;
    private final ParserElement element;

    public IndexNode(List<ParseTreeNode> children,
                     boolean isPresent,
                     boolean isCollection,
                     ParserRule rule,
                     ParserElement element) {
        this.children = children;
        this.isPresent = isPresent;
        this.isCollection = isCollection;
        this.rule = rule;
        this.element = element;
    }

    @Override
    public ParseTreeNode getItem(int index) {
        // Fixes the OR problem
        if (children == null || index >= children.size()) {
            return NULL;
        }
        return children.get(index);
    }

    @Override
    public boolean isPresent() {
        return isPresent;
    }

    @Override
    public boolean isPresent(ParserRule rule) {
        return isPresent && this.rule == rule;
    }

    @Override
    public boolean isPresent(ElementType type) {
        return isPresent && (element == null || element.getType() == type);
    }

    @Override
    public void failIfAbsent() {
        if (!isPresent) {
            throw new ParserException("This node does not contain a value");
        }
    }

    @Override
    public void failIfAbsent(ParserRule rule) {
        failIfAbsent();
        if (this.rule != rule) {
            throw new ParserException("Expecting rule " + rule +
                    ", but the rule of this node is " + this.rule);
        }
    }

    @Override
    public void failIfAbsent(ElementType type) {
        failIfAbsent();
        if (element.getType() != type) {
            throw new ParserException("Expecting type " + type +
                    ", but the type of this node is " + element.getType());
        }
    }

    @Override
    public Iterable<ParseTreeNode> asCollection() {
        failIfAbsent();
        if (!isCollection) {
            throw new ParserException("This node is not a collection");
        }
        return children;
    }

    @Override
    public boolean asBoolean() {
        if (rule != null) {
            throw new ParserException("Cannot be a rule and a boolean");
        }
        return isPresent;
    }

    @Override
    public String asString() {
        if (rule != null) {
            throw new ParserException("Cannot be a rule and a string");
        }
        return element.getValue();
    }

    @Override
    public String toString() {
        return "N{" +
                (rule == null ? "" : "R=" + rule + ", ") +
                "P=" + isPresent +
                (element == null ? "" : ", E=" + element) +
                (children == null ? "" : ", C=" + children) +
                '}';
    }
}
