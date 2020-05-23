package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class ExprlistStar extends NodeWrapper {

    public ExprlistStar(ParseTreeNode node) {
        super(node);
    }

    public ExprOrStar exprOrStar() {
        return get(0, ExprOrStar.class);
    }

    public List<ExprlistStar2> exprOrStars() {
        return getList(1, ExprlistStar2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistStar2 extends NodeWrapper {

        public ExprlistStar2(ParseTreeNode node) {
            super(node);
        }

        public ExprOrStar exprOrStar() {
            return get(1, ExprOrStar.class);
        }
    }
}
