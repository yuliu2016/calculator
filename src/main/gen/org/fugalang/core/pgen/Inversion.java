package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * inversion: 'not' 'inversion' | 'comparison'
 */
public final class Inversion extends NodeWrapper {

    public Inversion(ParseTreeNode node) {
        super(ParserRules.INVERSION, node);
    }

    public Inversion1 notInversion() {
        return get(0, Inversion1::new);
    }

    public boolean hasNotInversion() {
        return has(0, ParserRules.INVERSION_1);
    }

    public Comparison comparison() {
        return get(1, Comparison::new);
    }

    public boolean hasComparison() {
        return has(1, ParserRules.COMPARISON);
    }

    /**
     * 'not' 'inversion'
     */
    public static final class Inversion1 extends NodeWrapper {

        public Inversion1(ParseTreeNode node) {
            super(ParserRules.INVERSION_1, node);
        }

        public Inversion inversion() {
            return get(1, Inversion::new);
        }
    }
}
