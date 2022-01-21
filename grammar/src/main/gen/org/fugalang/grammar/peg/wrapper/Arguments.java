package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * arguments:
 * *   | ','.argument+ [',']
 */
public final class Arguments extends NodeWrapper {

    public Arguments(ParseTreeNode node) {
        super(node);
    }

    public List<Argument> arguments() {
        return getList(0, Argument::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
