package org.fugalang.core.parser;

/**
 * Profiler interface for {@link ParseTree}
 */
public interface Profiler {
    void init(ParserContext context);

    void markEnterFrame(int level, ParserRule rule);

    void markMemoHit(int level, int pos, ParserRule rule, ParseTreeNode result);

    void markExitFrame(boolean success, int level, ParserRule rule, int pos);

    void markElement(boolean success, int level, ElementType expected, ParserElement e);

    void markLiteral(boolean success, int level, String expected, ParserElement e);
}
