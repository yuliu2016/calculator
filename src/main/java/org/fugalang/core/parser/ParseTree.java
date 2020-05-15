package org.fugalang.core.parser;

public interface ParseTree {

    void enter(int level, ParserRule rule);

    void exit(boolean success);

    void enterCollection();

    void exitCollection();

    boolean consume(ElementType type);

    boolean consume(String literal);

    int position();

    boolean loopGuard(int position);
}
