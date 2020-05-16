package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * dotted_name: 'NAME' ('.' 'NAME')*
 */
public final class DottedName extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dotted_name", RuleType.Conjunction);

    public static DottedName of(ParseTreeNode node) {
        return new DottedName(node);
    }

    private DottedName(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public List<DottedName2> names() {
        return getList(1, DottedName2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) parseNames(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseNames(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!DottedName2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '.' 'NAME'
     */
    public static final class DottedName2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dotted_name:2", RuleType.Conjunction);

        public static DottedName2 of(ParseTreeNode node) {
            return new DottedName2(node);
        }

        private DottedName2(ParseTreeNode node) {
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
