package org.fugalang.core.parser;

public interface ParseTreeNode extends TreeStringElem {
    ParseTreeNode getItem(int index);

    boolean isPresent();

    void failIfAbsent(ElementType type);

    Iterable<ParseTreeNode> asCollection();

    boolean asBoolean();

    String asString();

    boolean isLeaf();

    int sizeOfChildren();
}
