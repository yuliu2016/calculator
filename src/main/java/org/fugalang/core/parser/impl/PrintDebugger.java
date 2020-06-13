package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.*;

public class PrintDebugger implements Profiler {
    @Override
    public void init(ParserContext context) {
        System.out.println("Starting parse tree");
    }

    @Override
    public void markEnterFrame(int level, ParserRule rule) {
        System.out.println("  ".repeat(level) + "Entering Frame: " + rule);
    }

    @Override
    public void markMemoHit(int level, int pos, ParserRule rule, ParseTreeNode result) {
        System.out.println("  ".repeat(level) + "Memo found for pos " + pos +
                " and rule " + rule + "; result=" + result);
    }

    @Override
    public void markExitFrame(boolean success, int level, ParserRule rule, int pos) {
        if (success) {
            System.out.println("  ".repeat(level) +
                    "Success in frame: " + rule + " at position " + pos);
        } else {
            System.out.println("  ".repeat(level) +
                    "Failure in frame: " + rule);
        }
    }

    @Override
    public void markElement(boolean success, int level, ElementType expected, ParserElement e) {
        if (success) {
            System.out.println("  ".repeat(level + 1) +
                    "Success in type " + expected + ": Token is '" +
                    fixLine(e.getValue()));
        } else {
            System.out.println("  ".repeat(level + 1) +
                    "Failure in type " + expected + ": Token is '" +
                    fixLine(e.getValue()) + "'");
        }
    }

    @Override
    public void markLiteral(boolean success, int level, String expected, ParserElement e) {
        if (success) {
            System.out.println("  ".repeat(level + 1) +
                    "Success in literal '" + expected + "'");
        } else {
            System.out.println("  ".repeat(level + 1) +
                    "Failure in literal '" + expected + "': Token is '" +
                    fixLine(e.getValue()) + "'");
        }
    }

    private static String fixLine(String s) {
        return s.replace("\r\n", "\\n")
                .replace("\n", "\\n") + "'";
    }
}
