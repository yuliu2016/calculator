package org.fugalang.core.parser;

public abstract class NodeWrapper {

    private final ParserRule rule;
    private final ParseTreeNode node;

    public NodeWrapper(ParserRule rule, ParseTreeNode node) {
        this.rule = rule;
        this.node = node;
    }

    protected abstract void buildRule();

    protected void addChoice(String name, Object choice) {
    }

    protected void addChoice(String name, boolean value) {
    }


    protected void addRequired(String name, boolean value) {
    }

    protected void addRequired(String name, Object value) {
    }

    protected void addOptional(String name, Object value) {
    }

    public ParseTreeNode getItem(int index) {
        return node.getItem(index);
    }

    @Override
    public String toString() {
        return "NodeWrapper{" +
                "rule=" + rule +
                ", node=" + node +
                '}';
    }
}
