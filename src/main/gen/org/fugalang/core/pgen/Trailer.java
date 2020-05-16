package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * trailer: '.' 'NAME' | 'parameters' | 'subscript'
 */
public final class Trailer extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("trailer", RuleType.Disjunction);

    public static Trailer of(ParseTreeNode node) {
        return new Trailer(node);
    }

    private Trailer(ParseTreeNode node) {
        super(RULE, node);
    }

    public Trailer1 name() {
        return get(0, Trailer1::of);
    }

    public boolean hasName() {
        return has(0, Trailer1.RULE);
    }

    public Parameters parameters() {
        return get(1, Parameters::of);
    }

    public boolean hasParameters() {
        return has(1, Parameters.RULE);
    }

    public Subscript subscript() {
        return get(2, Subscript::of);
    }

    public boolean hasSubscript() {
        return has(2, Subscript.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Trailer1.parse(t, lv + 1);
        r = r || Parameters.parse(t, lv + 1);
        r = r || Subscript.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '.' 'NAME'
     */
    public static final class Trailer1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("trailer:1", RuleType.Conjunction);

        public static Trailer1 of(ParseTreeNode node) {
            return new Trailer1(node);
        }

        private Trailer1(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(".");
            r = r && t.consume(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
