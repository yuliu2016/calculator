package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
 */
public final class CompOp extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_op", RuleType.Disjunction, true);

    public static CompOp of(ParseTreeNode node) {
        return new CompOp(node);
    }

    private CompOp(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTokenLess() {
        return getBoolean(0);
    }

    public boolean isTokenGreater() {
        return getBoolean(1);
    }

    public boolean isTokenEqual() {
        return getBoolean(2);
    }

    public boolean isTokenMoreEqual() {
        return getBoolean(3);
    }

    public boolean isTokenLessEqual() {
        return getBoolean(4);
    }

    public boolean isTokenNequal() {
        return getBoolean(5);
    }

    public boolean isTokenIn() {
        return getBoolean(6);
    }

    public CompOp8 compOp8() {
        return CompOp8.of(getItem(7));
    }

    public boolean hasCompOp8() {
        return hasItemOfRule(7, CompOp8.RULE);
    }

    public boolean isTokenIs() {
        return getBoolean(8);
    }

    public CompOp10 compOp10() {
        return CompOp10.of(getItem(9));
    }

    public boolean hasCompOp10() {
        return hasItemOfRule(9, CompOp10.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("<");
        result = result || parseTree.consumeToken(">");
        result = result || parseTree.consumeToken("==");
        result = result || parseTree.consumeToken(">=");
        result = result || parseTree.consumeToken("<=");
        result = result || parseTree.consumeToken("!=");
        result = result || parseTree.consumeToken("in");
        result = result || CompOp8.parse(parseTree, level + 1);
        result = result || parseTree.consumeToken("is");
        result = result || CompOp10.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }

    /**
     * 'not' 'in'
     */
    public static final class CompOp8 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("comp_op:8", RuleType.Conjunction, false);

        public static CompOp8 of(ParseTreeNode node) {
            return new CompOp8(node);
        }

        private CompOp8(ParseTreeNode node) {
            super(RULE, node);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("not");
            result = result && parseTree.consumeToken("in");

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * 'is' 'not'
     */
    public static final class CompOp10 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("comp_op:10", RuleType.Conjunction, false);

        public static CompOp10 of(ParseTreeNode node) {
            return new CompOp10(node);
        }

        private CompOp10(ParseTreeNode node) {
            super(RULE, node);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("is");
            result = result && parseTree.consumeToken("not");

            parseTree.exit(result);
            return result;
        }
    }
}
