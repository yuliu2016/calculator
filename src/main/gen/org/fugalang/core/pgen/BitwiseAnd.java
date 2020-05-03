package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("bitwise_and", RuleType.Conjunction, true);

    public static BitwiseAnd of(ParseTreeNode node) {
        return new BitwiseAnd(node);
    }

    private BitwiseAnd(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<BitwiseAnd2> bitwiseAnd2List;

    @Override
    protected void buildRule() {
        addRequired(shiftExpr());
        addRequired(bitwiseAnd2List());
    }

    public ShiftExpr shiftExpr() {
        var element = getItem(0);
        element.failIfAbsent(ShiftExpr.RULE);
        return ShiftExpr.of(element);
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        if (bitwiseAnd2List != null) {
            return bitwiseAnd2List;
        }
        List<BitwiseAnd2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(BitwiseAnd2.of(node));
        }
        bitwiseAnd2List = result == null ? Collections.emptyList() : result;
        return bitwiseAnd2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ShiftExpr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseAnd2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("bitwise_and:2", RuleType.Conjunction, false);

        public static BitwiseAnd2 of(ParseTreeNode node) {
            return new BitwiseAnd2(node);
        }

        private BitwiseAnd2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenBitAnd());
            addRequired(shiftExpr());
        }

        public boolean isTokenBitAnd() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ShiftExpr shiftExpr() {
            var element = getItem(1);
            element.failIfAbsent(ShiftExpr.RULE);
            return ShiftExpr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("&");
            result = result && ShiftExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
