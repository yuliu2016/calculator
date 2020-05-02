package org.fugalang.core.parser;

public interface ParseTreeNode {
    ParseTreeNode getItem(int index);

    ParseTreeNode orElseThrow();

    boolean isPresent();

    Iterable<ParseTreeNode> asCollection();

    boolean asBoolean();

    Object asObject();
}
