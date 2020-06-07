package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * func_type_hint:
 * *   | '<' expr '>'
 */
public final class FuncTypeHint extends NodeWrapper {

    public FuncTypeHint(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return new Expr(get(1));
    }
}
