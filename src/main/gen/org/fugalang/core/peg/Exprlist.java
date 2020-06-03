package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * exprlist: ','.expr+ [',']
 */
public final class Exprlist extends NodeWrapper {

    public Exprlist(ParseTreeNode node) {
        super(node);
    }

    public List<Expr> exprs() {
        return getList(0, Expr::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
