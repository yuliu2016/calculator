package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends NodeWrapper {

    public Disjunction(ParseTreeNode node) {
        super(ParserRules.DISJUNCTION, node);
    }

    public Conjunction conjunction() {
        return get(0, Conjunction.class);
    }

    public List<Disjunction2> orConjunctions() {
        return getList(1, Disjunction2.class);
    }

    /**
     * 'or' 'conjunction'
     */
    public static final class Disjunction2 extends NodeWrapper {

        public Disjunction2(ParseTreeNode node) {
            super(ParserRules.DISJUNCTION_2, node);
        }

        public Conjunction conjunction() {
            return get(1, Conjunction.class);
        }
    }
}
