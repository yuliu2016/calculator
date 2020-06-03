package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * targetlist: ','.target+ [',']
 */
public final class Targetlist extends NodeWrapper {

    public Targetlist(ParseTreeNode node) {
        super(node);
    }

    public List<Target> targets() {
        return getList(0, Target::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
