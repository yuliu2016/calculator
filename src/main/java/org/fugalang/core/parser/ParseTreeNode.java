package org.fugalang.core.parser;

public interface ParseTreeNode extends TreeStringElem {
    ParseTreeNode getItem(int index);

    boolean isPresent();

    boolean isPresent(ParserRule rule);

    boolean isPresent(ElementType type);

    void failIfAbsent();

    void failIfAbsent(ParserRule rule);

    void failIfAbsent(ElementType type);

    Iterable<ParseTreeNode> asCollection();

    boolean asBoolean();

    String asString();

    boolean isLeaf();

    int sizeOfChildren();
}
