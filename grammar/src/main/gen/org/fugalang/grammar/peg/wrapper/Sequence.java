package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sequence:
 * *   | primary+
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primarys() {
        return getList(0, Primary::new);
    }
}
