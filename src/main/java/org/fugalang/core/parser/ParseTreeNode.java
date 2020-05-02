package org.fugalang.core.parser;

public interface ParseTreeNode {
    ParseTreeNode getItem(int index);

    boolean isPresent();

    Iterable<ParseTreeNode> asCollection();

    boolean asBoolean();

    String asString();
}
