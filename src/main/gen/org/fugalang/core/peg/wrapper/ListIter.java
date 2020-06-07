package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * list_iter: '[' expr_or_star iterator ']'
 */
public final class ListIter extends NodeWrapper {

    public ListIter(ParseTreeNode node) {
        super(node);
    }

    public ExprOrStar exprOrStar() {
        return new ExprOrStar(get(1));
    }

    public Iterator iterator() {
        return new Iterator(get(2));
    }
}
