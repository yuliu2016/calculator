package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * conditional: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
 */
public final class Conditional extends NodeWrapper {

    public Conditional(ParseTreeNode node) {
        super(node);
    }

    public Disjunction disjunction() {
        return get(1, Disjunction.class);
    }

    public Disjunction disjunction1() {
        return get(3, Disjunction.class);
    }

    public Expr expr() {
        return get(5, Expr.class);
    }
}
