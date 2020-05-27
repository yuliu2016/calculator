package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * expr: conditional | funcdef | disjunction
 */
public final class Expr extends NodeWrapper {

    public Expr(ParseTreeNode node) {
        super(node);
    }

    public Conditional conditional() {
        return get(0, Conditional.class);
    }

    public boolean hasConditional() {
        return has(0);
    }

    public Funcdef funcdef() {
        return get(1, Funcdef.class);
    }

    public boolean hasFuncdef() {
        return has(1);
    }

    public Disjunction disjunction() {
        return get(2, Disjunction.class);
    }

    public boolean hasDisjunction() {
        return has(2);
    }
}
