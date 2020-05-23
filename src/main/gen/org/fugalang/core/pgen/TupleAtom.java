package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * tuple_atom: '(' ['named_expr_list'] ')'
 */
public final class TupleAtom extends NodeWrapper {

    public TupleAtom(ParseTreeNode node) {
        super(ParserRules.TUPLE_ATOM, node);
    }

    public NamedExprList namedExprList() {
        return get(1, NamedExprList.class);
    }

    public boolean hasNamedExprList() {
        return has(1);
    }
}