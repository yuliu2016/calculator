package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<ShiftExpr2> shiftExpr2List;

    @Override
    protected void buildRule() {
        addRequired(sum());
        addRequired(shiftExpr2List());
    }

    public Sum sum() {
        var element = getItem(0);
        element.failIfAbsent(Sum.RULE);
        return Sum.of(element);
    }

    public List<ShiftExpr2> shiftExpr2List() {
        if (shiftExpr2List != null) {
            return shiftExpr2List;
        }
        List<ShiftExpr2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(ShiftExpr2.of(node));
        }
        shiftExpr2List = result == null ? Collections.emptyList() : result;
        return shiftExpr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(shiftExpr21());
            addRequired(sum());
        }

        public ShiftExpr21 shiftExpr21() {
            var element = getItem(0);
            element.failIfAbsent(ShiftExpr21.RULE);
            return ShiftExpr21.of(element);
        }

        public Sum sum() {
            var element = getItem(1);
            element.failIfAbsent(Sum.RULE);
            return Sum.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addChoice(isTokenLshift(), "<<");
            addChoice(isTokenRshift(), ">>");
        }

        public boolean isTokenLshift() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenRshift() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("<<");
            result = result || parseTree.consumeToken(">>");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
