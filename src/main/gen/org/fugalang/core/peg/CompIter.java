package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * comp_iter: comp_for | comp_if
 */
public final class CompIter extends NodeWrapper {

    public CompIter(ParseTreeNode node) {
        super(node);
    }

    public CompFor compFor() {
        return new CompFor(get(0));
    }

    public boolean hasCompFor() {
        return has(0);
    }

    public CompIf compIf() {
        return new CompIf(get(1));
    }

    public boolean hasCompIf() {
        return has(1);
    }
}
