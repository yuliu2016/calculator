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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("<");
        r = r || t.consumeToken(">");
        r = r || t.consumeToken("==");
        r = r || t.consumeToken(">=");
        r = r || t.consumeToken("<=");
        r = r || t.consumeToken("!=");
        r = r || t.consumeToken("in");
        r = r || CompOp8.parse(t, l + 1);
        r = r || t.consumeToken("is");
        r = r || CompOp10.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("not");
            r = r && t.consumeToken("in");
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("is");
            r = r && t.consumeToken("not");
            t.exit(r);
            return r;
        }
    }
}
