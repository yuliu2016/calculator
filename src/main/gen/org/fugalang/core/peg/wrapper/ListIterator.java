package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * list_iterator (allow_whitespace=true):
 * *   | expr_or_star iterator
 */
public final class ListIterator extends NodeWrapper {

    public ListIterator(ParseTreeNode node) {
        super(node);
    }

    public ExprOrStar exprOrStar() {
        return new ExprOrStar(get(0));
    }

    public Iterator iterator() {
        return new Iterator(get(1));
    }
}
