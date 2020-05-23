package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * list_atom: '[' ['named_expr_list'] ']'
 */
public final class ListAtom extends NodeWrapper {

    public ListAtom(ParseTreeNode node) {
        super(ParserRules.LIST_ATOM, node);
    }

    public NamedExprList namedExprList() {
        return get(1, NamedExprList.class);
    }

    public boolean hasNamedExprList() {
        return has(1);
    }
}