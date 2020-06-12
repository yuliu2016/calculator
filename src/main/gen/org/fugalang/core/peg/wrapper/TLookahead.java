package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * t_lookahead:
 * *   | '.'
 * *   | '('
 * *   | '['
 */
public final class TLookahead extends NodeWrapper {

    public TLookahead(ParseTreeNode node) {
        super(node);
    }

    public boolean isDot() {
        return is(0);
    }

    public boolean isLpar() {
        return is(1);
    }

    public boolean isLsqb() {
        return is(2);
    }
}
