package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * primary: 'atom' 'trailer'* ['block_suite']
 */
public final class Primary extends NodeWrapper {

    public Primary(ParseTreeNode node) {
        super(node);
    }

    public Atom atom() {
        return get(0, Atom.class);
    }

    public List<Trailer> trailers() {
        return getList(1, Trailer.class);
    }

    public BlockSuite blockSuite() {
        return get(2, BlockSuite.class);
    }

    public boolean hasBlockSuite() {
        return has(2);
    }
}
