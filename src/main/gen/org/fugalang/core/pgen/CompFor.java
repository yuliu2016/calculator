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
        return get(1, Targetlist.class);
    }

    public Disjunction disjunction() {
        return get(3, Disjunction.class);
    }

    public CompIter compIter() {
        return get(4, CompIter.class);
    }

    public boolean hasCompIter() {
        return has(4);
    }
}
