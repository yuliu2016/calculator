package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * iter_for: 'for' targetlist 'in' disjunction [iter_if]
 */
public final class IterFor extends NodeWrapper {

    public IterFor(ParseTreeNode node) {
        super(node);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(1));
    }

    public Disjunction disjunction() {
        return new Disjunction(get(3));
    }

    public IterIf iterIf() {
        return new IterIf(get(4));
    }

    public boolean hasIterIf() {
        return has(4);
    }
}
