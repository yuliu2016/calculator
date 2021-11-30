package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * alt_list:
 * *   | sequence alternative*
 */
public final class AltList extends NodeWrapper {

    public AltList(ParseTreeNode node) {
        super(node);
    }

    public Sequence sequence() {
        return new Sequence(get(0));
    }

    public List<Alternative> alternatives() {
        return getList(1, Alternative::new);
    }
}
