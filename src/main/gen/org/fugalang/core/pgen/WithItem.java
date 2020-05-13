package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_item", RuleType.Conjunction, true);

    public static WithItem of(ParseTreeNode node) {
        return new WithItem(node);
    }

    private WithItem(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public WithItem2 withItem2() {
        return WithItem2.of(getItem(1));
    }

    public boolean hasWithItem2() {
        return hasItemOfRule(1, WithItem2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        if (result) WithItem2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class WithItem2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_item:2", RuleType.Conjunction, false);

        public static WithItem2 of(ParseTreeNode node) {
            return new WithItem2(node);
        }

        private WithItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenAs() {
            return true;
        }

        public String name() {
            return getItemOfType(1,TokenType.NAME);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
