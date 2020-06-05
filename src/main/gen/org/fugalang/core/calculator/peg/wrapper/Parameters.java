package org.fugalang.core.calculator.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * parameters: ','.sum+ [',']
 */
public final class Parameters extends NodeWrapper {

    public Parameters(ParseTreeNode node) {
        super(node);
    }

    public List<Sum> sums() {
        return getList(0, Sum::new);
    }

    public boolean isComma() {
        return is(1);
    }
}
