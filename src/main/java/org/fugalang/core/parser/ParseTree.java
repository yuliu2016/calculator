package org.fugalang.core.parser;

public interface ParseTree {

    void enter(int level, ParserRule rule);

    void exit(boolean success);

    void enterCollection();

    void exitCollection();

    boolean consumeToken(ElementType type);

    boolean consumeToken(String literal);

    int position();

    boolean guardLoopExit(int position);
}
