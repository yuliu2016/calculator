package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * slicelist: 'slice' (',' 'slice')* [',']
 */
public final class Slicelist extends NodeWrapper {

    public Slicelist(ParseTreeNode node) {
        super(ParserRules.SLICELIST, node);
    }

    public Slice slice() {
        return get(0, Slice::new);
    }

    public List<Slicelist2> slices() {
        return getList(1, Slicelist2::new);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'slice'
     */
    public static final class Slicelist2 extends NodeWrapper {

        public Slicelist2(ParseTreeNode node) {
            super(ParserRules.SLICELIST_2, node);
        }

        public Slice slice() {
            return get(1, Slice::new);
        }
    }
}
