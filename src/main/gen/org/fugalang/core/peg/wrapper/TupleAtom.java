package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * tuple_atom: '(' [named_expr_list] ')'
 */
public final class TupleAtom extends NodeWrapper {

    public TupleAtom(ParseTreeNode node) {
        super(node);
    }

    public NamedExprList namedExprList() {
        return new NamedExprList(get(1));
    }

    public boolean hasNamedExprList() {
        return has(1);
    }
}
