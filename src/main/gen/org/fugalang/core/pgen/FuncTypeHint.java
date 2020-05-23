package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * func_type_hint: '<' 'expr' '>'
 */
public final class FuncTypeHint extends NodeWrapper {

    public FuncTypeHint(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }
}
