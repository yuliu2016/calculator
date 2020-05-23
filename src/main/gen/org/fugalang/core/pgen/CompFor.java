package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * comp_for: 'for' 'targetlist' 'in' 'disjunction' ['comp_iter']
 */
public final class CompFor extends NodeWrapper {

    public CompFor(ParseTreeNode node) {
        super(ParserRules.COMP_FOR, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist::new);
    }

    public Disjunction disjunction() {
        return get(3, Disjunction::new);
    }

    public CompIter compIter() {
        return get(4, CompIter::new);
    }

    public boolean hasCompIter() {
        return has(4);
    }
}
