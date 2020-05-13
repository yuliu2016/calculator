package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * shift_expr: 'sum' (('<<' | '>>') 'sum')*
 */
public final class ShiftExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("shift_expr", RuleType.Conjunction, true);

    public static ShiftExpr of(ParseTreeNode node) {
        return new ShiftExpr(node);
    }

    private ShiftExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Sum sum() {
        return Sum.of(getItem(0));
    }

    public List<ShiftExpr2> shiftExpr2List() {
        return getList(1, ShiftExpr2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Sum.parse(parseTree, level + 1);
        if (result) parseShiftExpr2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseShiftExpr2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ShiftExpr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ('<<' | '>>') 'sum'
     */
    public static final class ShiftExpr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("shift_expr:2", RuleType.Conjunction, false);

        public static ShiftExpr2 of(ParseTreeNode node) {
            return new ShiftExpr2(node);
        }

        private ShiftExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ShiftExpr21 shiftExpr21() {
            return ShiftExpr21.of(getItem(0));
        }

        public Sum sum() {
            return Sum.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = ShiftExpr21.parse(parseTree, level + 1);
            result = result && Sum.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '<<' | '>>'
     */
    public static final class ShiftExpr21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("shift_expr:2:1", RuleType.Disjunction, false);

        public static ShiftExpr21 of(ParseTreeNode node) {
            return new ShiftExpr21(node);
        }

        private ShiftExpr21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenLshift() {
            return getBoolean(0);
        }

        public boolean isTokenRshift() {
            return getBoolean(1);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("<<");
            result = result || parseTree.consumeToken(">>");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
