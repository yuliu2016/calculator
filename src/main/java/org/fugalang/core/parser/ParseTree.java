package org.fugalang.core.parser;

public interface ParseTree {

    Marker enter(int level, ParserRule rule);

    void exit(int level, Marker marker, boolean success);

    void enterSection();

    void exitSection();

    void enterCollection();

    void exitCollection();

    boolean consumeTokenType(String type);

    boolean consumeTokenLiteral(String literal);

    int position();

    boolean guardLoopExit(int position);

    interface Marker {
    }
}
