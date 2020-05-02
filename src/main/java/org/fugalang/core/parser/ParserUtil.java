package org.fugalang.core.parser;

public class ParserUtil {
    public static boolean recursionGuard(int level, ParserRule rule) {
        if (level > 1000) {
            System.err.println("Max recursion level reached in rule " + rule);
            return false;
        }
        return true;
    }
}
