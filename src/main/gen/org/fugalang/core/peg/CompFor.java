package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * comp_for: 'for' targetlist 'in' disjunction [comp_iter]
 */
public final class CompFor extends NodeWrapper {

    public CompFor(ParseTreeNode node) {
        super(node);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(1));
    }

    public Disjunction disjunction() {
        return new Disjunction(get(3));
    }

    public CompIter compIter() {
        return new CompIter(get(4));
    }

    public boolean hasCompIter() {
        return has(4);
    }
}
