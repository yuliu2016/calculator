package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * conditional: 'if' disjunction '?' disjunction ':' expr
 */
public final class Conditional extends NodeWrapper {

    public Conditional(ParseTreeNode node) {
        super(node);
    }

    public Disjunction disjunction() {
        return new Disjunction(get(1));
    }

    public Disjunction disjunction1() {
        return new Disjunction(get(3));
    }

    public Expr expr() {
        return new Expr(get(5));
    }
}
