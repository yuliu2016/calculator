package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * dotted_as_name: 'dotted_name' ['as' 'NAME']
 */
public final class DottedAsName extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dotted_as_name", RuleType.Conjunction);

    public static DottedAsName of(ParseTreeNode node) {
        return new DottedAsName(node);
    }

    private DottedAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedName dottedName() {
        return DottedName.of(get(0));
    }

    public DottedAsName2 dottedAsName2() {
        return DottedAsName2.of(get(1));
    }

    public boolean hasDottedAsName2() {
        return has(1, DottedAsName2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = DottedName.parse(t, lv + 1);
        if (r) DottedAsName2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class DottedAsName2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dotted_as_name:2", RuleType.Conjunction);

        public static DottedAsName2 of(ParseTreeNode node) {
            return new DottedAsName2(node);
        }

        private DottedAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("as");
            r = r && t.consume(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
