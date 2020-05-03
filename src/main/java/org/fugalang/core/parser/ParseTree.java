package org.fugalang.core.parser;

public interface ParseTree {

    ParseTreeMarker enter(int level, ParserRule rule);

    void exit(int level, ParseTreeMarker marker, boolean success);

    void enterCollection();

    void exitCollection();

    boolean consumeToken(ElementType type);

    boolean consumeToken(String literal);

    int position();

    boolean guardLoopExit(int position);

}
