package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * slicelist: slice (',' slice)* [',']
 */
public final class Slicelist extends NodeWrapper {

    public Slicelist(ParseTreeNode node) {
        super(node);
    }

    public Slice slice() {
        return get(0, Slice.class);
    }

    public List<Slicelist2> slices() {
        return getList(1, Slicelist2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' slice
     */
    public static final class Slicelist2 extends NodeWrapper {

        public Slicelist2(ParseTreeNode node) {
            super(node);
        }

        public Slice slice() {
            return get(1, Slice.class);
        }
    }
}
