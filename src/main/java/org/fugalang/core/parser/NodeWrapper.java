package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;
import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

public abstract class NodeWrapper implements TreeStringElem {

    private final ParserRule rule;
    private final ParseTreeNode node;
    private NodeDelegate delegate;

    public NodeWrapper(ParserRule rule, ParseTreeNode node) {
        this.rule = rule;
        this.node = node;
    }

    protected abstract void buildRule();

    private NodeDelegate getOrCreateDelegate() {
        if (delegate == null) {
            delegate = new WrapperDelegate(rule);
            buildRule();
            delegate.didBuildRule();
        }
        return delegate;
    }

    private NodeDelegate getDelegate() {
        if (delegate == null) {
            throw new IllegalStateException("Delegate not initialized");
        }
        return delegate;
    }

    public ParserRule getRule() {
        return rule;
    }

    protected void addChoice(Object choice) {
        getDelegate().addChoice(choice);
    }


    protected void addChoice(boolean value, String literal) {
        getDelegate().addChoice(value, literal);
    }

    protected void addRequired(boolean value, String literal) {
        getDelegate().addRequired(value, literal);
    }

    protected void addRequired(Object value) {
        getDelegate().addRequired(value);
    }

    protected void addOptional(Object value) {
        getDelegate().addOptional(value);
    }

    protected void addOptional(boolean value, String literal) {
        getDelegate().addOptional(value, literal);
    }

    public ParseTreeNode getItem(int index) {
        return node.getItem(index);
    }

    @Override
    public String toString() {
        return getOrCreateDelegate().simpleString();
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        getOrCreateDelegate().buildString(builder);
    }

    public String prettyFormat(int indent) {
        return ParseTreePPrint.format(this, indent);
    }
}
