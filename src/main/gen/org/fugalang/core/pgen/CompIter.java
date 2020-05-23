package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * comp_iter: 'comp_for' | 'comp_if'
 */
public final class CompIter extends NodeWrapper {

    public CompIter(ParseTreeNode node) {
        super(ParserRules.COMP_ITER, node);
    }

    public CompFor compFor() {
        return get(0, CompFor::new);
    }

    public boolean hasCompFor() {
        return has(0, ParserRules.COMP_FOR);
    }

    public CompIf compIf() {
        return get(1, CompIf::new);
    }

    public boolean hasCompIf() {
        return has(1, ParserRules.COMP_IF);
    }
}
