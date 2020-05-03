package org.fugalang.core.parser;

import org.fugalang.core.pprint.ParseTreePPrint;
import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

public abstract class NodeWrapper implements TreeStringElem {

    private final ParserRule rule;
    private final ParseTreeNode node;
    private NodeDelegate delegate;
    private boolean didBuildRule;

    public NodeWrapper(ParserRule rule, ParseTreeNode node) {
        this.rule = rule;
        this.node = node;
    }

    protected abstract void buildRule();

    private NodeDelegate getOrCreateDelegate() {
        if (delegate == null) {
            delegate = new WrapperDelegate(rule);
            buildRule();
            didBuildRule = true;
        }
        return delegate;
    }

    private NodeDelegate getDelegate() {
        if (delegate == null) {
            throw new IllegalStateException("Delegate not initialized");
        }
        if (didBuildRule) {
            throw new IllegalStateException("Node has already been built");
        }
        return delegate;
    }

    public ParserRule getRule() {
        return rule;
    }

    protected void addChoice(Object choice) {
        getDelegate().addChoice(choice);
    }

    protected void addChoice(boolean value) {
        getDelegate().addChoice(value);
    }

    protected void addRequired(boolean value) {
        getDelegate().addRequired(value);
    }

    protected void addRequired(Object value) {
        getDelegate().addRequired(value);
    }

    protected void addOptional(Object value) {
        getDelegate().addOptional(value);
    }

    public ParseTreeNode getItem(int index) {
        return node.getItem(index);
    }

    @Override
    public String toString() {
        return getOrCreateDelegate().asString();
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        getOrCreateDelegate().buildString(builder);
    }

    public String prettyFormat(int indent) {
        return ParseTreePPrint.format(this, indent);
    }
}
