package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * primary: 'atom' 'trailer'* ['block_suite']
 */
public final class Primary extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("primary", RuleType.Conjunction);

    public static Primary of(ParseTreeNode node) {
        return new Primary(node);
    }

    private Primary(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom atom() {
        return get(0, Atom::of);
    }

    public List<Trailer> trailers() {
        return getList(1, Trailer::of);
    }

    public BlockSuite blockSuite() {
        return get(2, BlockSuite::of);
    }

    public boolean hasBlockSuite() {
        return has(2, BlockSuite.RULE);
    }
}
