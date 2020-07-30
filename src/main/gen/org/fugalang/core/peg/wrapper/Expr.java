package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * expr:
 * *   | conditional
 * *   | disjunction
 */
public final class Expr extends NodeWrapper {

    public Expr(ParseTreeNode node) {
        super(node);
    }

    public Conditional conditional() {
        return new Conditional(get(0));
    }

    public boolean hasConditional() {
        return has(0);
    }

    public Disjunction disjunction() {
        return new Disjunction(get(1));
    }

    public boolean hasDisjunction() {
        return has(1);
    }
}
