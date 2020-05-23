package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends NodeWrapper {

    public Conjunction(ParseTreeNode node) {
        super(FugaRules.CONJUNCTION, node);
    }

    public Inversion inversion() {
        return get(0, Inversion.class);
    }

    public List<Conjunction2> andInversions() {
        return getList(1, Conjunction2.class);
    }

    /**
     * 'and' 'inversion'
     */
    public static final class Conjunction2 extends NodeWrapper {

        public Conjunction2(ParseTreeNode node) {
            super(FugaRules.CONJUNCTION_2, node);
        }

        public Inversion inversion() {
            return get(1, Inversion.class);
        }
    }
}
