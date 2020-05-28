package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * and_rule: repeat+
 */
public final class AndRule extends NodeWrapper {

    public AndRule(ParseTreeNode node) {
        super(node);
    }

    public List<Repeat> repeats() {
        return getList(0, Repeat.class);
    }
}
