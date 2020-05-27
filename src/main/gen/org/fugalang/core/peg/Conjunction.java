package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * conjunction: inversion ('and' inversion)*
 */
public final class Conjunction extends NodeWrapper {

    public Conjunction(ParseTreeNode node) {
        super(node);
    }

    public Inversion inversion() {
        return get(0, Inversion.class);
    }

    public List<Conjunction2> andInversions() {
        return getList(1, Conjunction2.class);
    }

    /**
     * 'and' inversion
     */
    public static final class Conjunction2 extends NodeWrapper {

        public Conjunction2(ParseTreeNode node) {
            super(node);
        }

        public Inversion inversion() {
            return get(1, Inversion.class);
        }
    }
}
