package org.fugalang.core.parser;

public class ParserUtil {
    public static boolean recursionGuard(int level, ParserRule rule) {
        if (level > 300) {
            throw new ParserException("Max recursion level of 300 reached in rule " + rule);
        }
        return true;
    }
}
