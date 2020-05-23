package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * primary: 'atom' 'trailer'* ['block_suite']
 */
public final class Primary extends NodeWrapper {

    public Primary(ParseTreeNode node) {
        super(ParserRules.PRIMARY, node);
    }

    public Atom atom() {
        return get(0, Atom::new);
    }

    public List<Trailer> trailers() {
        return getList(1, Trailer::new);
    }

    public BlockSuite blockSuite() {
        return get(2, BlockSuite::new);
    }

    public boolean hasBlockSuite() {
        return has(2, ParserRules.BLOCK_SUITE);
    }
}
