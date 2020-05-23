package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("disjunction", RuleType.Conjunction);

    public static Disjunction of(ParseTreeNode node) {
        return new Disjunction(node);
    }

    private Disjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Conjunction conjunction() {
        return get(0, Conjunction::of);
    }

    public List<Disjunction2> orConjunctions() {
        return getList(1, Disjunction2::of);
    }

    /**
     * 'or' 'conjunction'
     */
    public static final class Disjunction2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("disjunction:2", RuleType.Conjunction);

        public static Disjunction2 of(ParseTreeNode node) {
            return new Disjunction2(node);
        }

        private Disjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Conjunction conjunction() {
            return get(1, Conjunction::of);
        }
    }
}
