package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * exprlist_star: ','.expr_or_star+ [',']
 */
public final class ExprlistStar extends NodeWrapper {

    public ExprlistStar(ParseTreeNode node) {
        super(node);
    }

    public List<ExprOrStar> exprOrStars() {
        return getList(0, ExprOrStar::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
