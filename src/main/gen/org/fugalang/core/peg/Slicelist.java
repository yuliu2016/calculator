package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * slicelist: ','.slice+ [',']
 */
public final class Slicelist extends NodeWrapper {

    public Slicelist(ParseTreeNode node) {
        super(node);
    }

    public List<Slice> slices() {
        return getList(0, Slice.class);
    }

    public boolean isComma() {
        return is(1);
    }
}
