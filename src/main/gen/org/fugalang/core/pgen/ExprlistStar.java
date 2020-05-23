package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class ExprlistStar extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("exprlist_star", RuleType.Conjunction);

    public static ExprlistStar of(ParseTreeNode node) {
        return new ExprlistStar(node);
    }

    private ExprlistStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprOrStar exprOrStar() {
        return get(0, ExprOrStar::of);
    }

    public List<ExprlistStar2> exprOrStars() {
        return getList(1, ExprlistStar2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistStar2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("exprlist_star:2", RuleType.Conjunction);

        public static ExprlistStar2 of(ParseTreeNode node) {
            return new ExprlistStar2(node);
        }

        private ExprlistStar2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return get(1, ExprOrStar::of);
        }
    }
}
