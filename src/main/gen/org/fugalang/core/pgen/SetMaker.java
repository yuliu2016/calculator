package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * set_maker: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class SetMaker extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("set_maker", RuleType.Conjunction);

    public static SetMaker of(ParseTreeNode node) {
        return new SetMaker(node);
    }

    private SetMaker(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprOrStar exprOrStar() {
        return get(0, ExprOrStar::of);
    }

    public List<SetMaker2> exprOrStars() {
        return getList(1, SetMaker2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class SetMaker2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("set_maker:2", RuleType.Conjunction);

        public static SetMaker2 of(ParseTreeNode node) {
            return new SetMaker2(node);
        }

        private SetMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return get(1, ExprOrStar::of);
        }
    }
}
