package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * targetlist_sp:
 * *   | targetlist
 */
public final class TargetlistSp extends NodeWrapper {

    public TargetlistSp(ParseTreeNode node) {
        super(node);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(0));
    }
}
