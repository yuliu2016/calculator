package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * dotted_as_name: 'dotted_name' ['as' 'NAME']
 */
public final class DottedAsName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_as_name", RuleType.Conjunction, true);

    public static DottedAsName of(ParseTreeNode node) {
        return new DottedAsName(node);
    }

    private DottedAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedName dottedName() {
        return DottedName.of(getItem(0));
    }

    public DottedAsName2 dottedAsName2() {
        return DottedAsName2.of(getItem(1));
    }

    public boolean hasDottedAsName2() {
        return hasItemOfRule(1, DottedAsName2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = DottedName.parse(t, l + 1);
        if (r) DottedAsName2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class DottedAsName2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_as_name:2", RuleType.Conjunction, false);

        public static DottedAsName2 of(ParseTreeNode node) {
            return new DottedAsName2(node);
        }

        private DottedAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("as");
            r = r && t.consumeToken(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
