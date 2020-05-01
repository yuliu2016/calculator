package org.fugalang.core.parser;

public interface ParseTreeNode {
    ParseTreeNode getItem(int index);

    public ParseTreeNode orElseThrow();

    public boolean isPresent();

    Iterable<ParseTreeNode> asCollection();

    boolean asBoolean();
}
