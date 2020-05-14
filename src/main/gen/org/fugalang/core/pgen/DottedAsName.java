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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = DottedName.parse(parseTree, level + 1);
        if (result) DottedAsName2.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(result);
            return result;
        }
    }
}
