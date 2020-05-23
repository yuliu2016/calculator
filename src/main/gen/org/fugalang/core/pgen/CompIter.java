package org.fugalang.core.pgen;

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
        return get(0, CompFor.class);
    }

    public boolean hasCompFor() {
        return has(0);
    }

    public CompIf compIf() {
        return get(1, CompIf.class);
    }

    public boolean hasCompIf() {
        return has(1);
    }
}
