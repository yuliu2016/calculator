package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * named_expr_list: 'named_expr_star' (',' 'named_expr_star')* [',']
 */
public final class NamedExprList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("named_expr_list", RuleType.Conjunction);

    public static NamedExprList of(ParseTreeNode node) {
        return new NamedExprList(node);
    }

    private NamedExprList(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprStar namedExprStar() {
        return get(0, NamedExprStar::of);
    }

    public List<NamedExprList2> namedExprStars() {
        return getList(1, NamedExprList2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'named_expr_star'
     */
    public static final class NamedExprList2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("named_expr_list:2", RuleType.Conjunction);

        public static NamedExprList2 of(ParseTreeNode node) {
            return new NamedExprList2(node);
        }

        private NamedExprList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public NamedExprStar namedExprStar() {
            return get(1, NamedExprStar::of);
        }
    }
}
