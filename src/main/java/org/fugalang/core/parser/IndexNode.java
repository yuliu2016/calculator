package org.fugalang.core.parser;

import java.util.List;

public class IndexNode implements ParseTreeNode {

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
        return children.get(index);
    }

    @Override
    public boolean isPresent() {
        return isPresent;
    }

    @Override
    public boolean isPresent(ParserRule rule) {
        return false;
    }

    @Override
    public boolean isPresent(ElementType type) {
        return false;
    }

    @Override
    public void failIfAbsent() {
        if (!isPresent) {
            throw new ParserException();
        }
    }

    @Override
    public void failIfAbsent(ParserRule rule) {
        if (!isPresent || this.rule != rule) {
            throw new ParserException();
        }
    }

    @Override
    public void failIfAbsent(ElementType type) {
        if (!isPresent || element.getType() != type) {
            throw new ParserException();
        }
    }

    @Override
    public Iterable<ParseTreeNode> asCollection() {
        if (!isPresent || !isCollection) {
            throw new ParserException();
        }
        return children;
    }

    @Override
    public boolean asBoolean() {
        return isPresent;
    }

    @Override
    public String asString() {
        return element.getValue();
    }
}
