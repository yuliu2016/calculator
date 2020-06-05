package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * arglist: ','.argument+ [',']
 */
public final class Arglist extends NodeWrapper {

    public Arglist(ParseTreeNode node) {
        super(node);
    }

    public List<Argument> arguments() {
        return getList(0, Argument::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
