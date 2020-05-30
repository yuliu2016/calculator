package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * named_expr_list: ','.named_expr_star+ [',']
 */
public final class NamedExprList extends NodeWrapper {

    public NamedExprList(ParseTreeNode node) {
        super(node);
    }

    public List<NamedExprStar> namedExprStars() {
        return getList(0, NamedExprStar.class);
    }

    public boolean isComma() {
        return is(1);
    }
}
