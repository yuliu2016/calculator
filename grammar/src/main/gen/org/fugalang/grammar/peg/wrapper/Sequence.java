package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sequence:
 * *   | primary+ [result_clause]
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primaries() {
        return getList(0, Primary::new);
    }

    public ResultClause resultClause() {
        return new ResultClause(get(1));
    }

    public boolean hasResultClause() {
        return has(1);
    }
}
