package org.fugalang.core.parser;

public class ParserUtil {
    public static boolean recursionGuard(int level, ParserRule rule) {
        if (level > 100) {
            throw new ParserException("Max recursion level of 100 reached in rule " + rule);
        }
        return true;
    }
}
