package org.fugalang.core.parser;

public interface ParseTree {

    static boolean recursionGuard(int level, String funcName) {
        if (level > 1000) {
            System.err.println("Max recursion level reached in rule " + funcName);
            return false;
        }
        return true;
    }

    Marker enter(int level, String ruleName);

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
