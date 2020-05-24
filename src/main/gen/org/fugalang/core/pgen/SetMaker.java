package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * set_maker: expr_or_star (',' expr_or_star)* [',']
 */
public final class SetMaker extends NodeWrapper {

    public SetMaker(ParseTreeNode node) {
        super(node);
    }

    public ExprOrStar exprOrStar() {
        return get(0, ExprOrStar.class);
    }

    public List<SetMaker2> commaExprOrStars() {
        return getList(1, SetMaker2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' expr_or_star
     */
    public static final class SetMaker2 extends NodeWrapper {

        public SetMaker2(ParseTreeNode node) {
            super(node);
        }

        public ExprOrStar exprOrStar() {
            return get(1, ExprOrStar.class);
        }
    }
}
