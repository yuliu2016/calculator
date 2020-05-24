package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * named_expr_list: named_expr_star (',' named_expr_star)* [',']
 */
public final class NamedExprList extends NodeWrapper {

    public NamedExprList(ParseTreeNode node) {
        super(node);
    }

    public NamedExprStar namedExprStar() {
        return get(0, NamedExprStar.class);
    }

    public List<NamedExprList2> commaNamedExprStars() {
        return getList(1, NamedExprList2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' named_expr_star
     */
    public static final class NamedExprList2 extends NodeWrapper {

        public NamedExprList2(ParseTreeNode node) {
            super(node);
        }

        public NamedExprStar namedExprStar() {
            return get(1, NamedExprStar.class);
        }
    }
}
