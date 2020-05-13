package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * dotted_name: 'NAME' ('.' 'NAME')*
 */
public final class DottedName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_name", RuleType.Conjunction, true);

    public static DottedName of(ParseTreeNode node) {
        return new DottedName(node);
    }

    private DottedName(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return getItemOfType(0,TokenType.NAME);
    }

    public List<DottedName2> dottedName2List() {
        return getList(1, DottedName2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) parseDottedName2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseDottedName2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DottedName2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * '.' 'NAME'
     */
    public static final class DottedName2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_name:2", RuleType.Conjunction, false);

        public static DottedName2 of(ParseTreeNode node) {
            return new DottedName2(node);
        }

        private DottedName2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenDot() {
            return true;
        }

        public String name() {
            return getItemOfType(1,TokenType.NAME);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(".");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
