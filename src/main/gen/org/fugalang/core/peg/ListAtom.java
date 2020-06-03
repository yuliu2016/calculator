package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * list_atom: '[' [named_expr_list] ']'
 */
public final class ListAtom extends NodeWrapper {

    public ListAtom(ParseTreeNode node) {
        super(node);
    }

    public NamedExprList namedExprList() {
        return new NamedExprList(get(1));
    }

    public boolean hasNamedExprList() {
        return has(1);
    }
}
