package org.fugalang.core.calculator.pgen;

import org.fugalang.core.calculator.pgen.parser.CalculatorRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sum: 'term' (('+' | '-') 'term')*
 */
public final class Sum extends NodeWrapper {

    public Sum(ParseTreeNode node) {
        super(CalculatorRules.SUM, node);
    }

    public Term term() {
        return get(0, Term.class);
    }

    public List<Sum2> sum2s() {
        return getList(1, Sum2.class);
    }

    /**
     * ('+' | '-') 'term'
     */
    public static final class Sum2 extends NodeWrapper {

        public Sum2(ParseTreeNode node) {
            super(CalculatorRules.SUM_2, node);
        }

        public Sum21 plusOrMinus() {
            return get(0, Sum21.class);
        }

        public Term term() {
            return get(1, Term.class);
        }
    }

    /**
     * '+' | '-'
     */
    public static final class Sum21 extends NodeWrapper {

        public Sum21(ParseTreeNode node) {
            super(CalculatorRules.SUM_2_1, node);
        }

        public boolean isPlus() {
            return is(0);
        }

        public boolean isMinus() {
            return is(1);
        }
    }
}
