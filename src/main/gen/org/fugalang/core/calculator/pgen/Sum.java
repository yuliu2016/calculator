package org.fugalang.core.calculator.pgen;

import org.fugalang.core.calculator.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sum: 'term' (('+' | '-') 'term')*
 */
public final class Sum extends NodeWrapper {

    public Sum(ParseTreeNode node) {
        super(ParserRules.SUM, node);
    }

    public Term term() {
        return get(0, Term::new);
    }

    public List<Sum2> sum2s() {
        return getList(1, Sum2::new);
    }

    /**
     * ('+' | '-') 'term'
     */
    public static final class Sum2 extends NodeWrapper {

        public Sum2(ParseTreeNode node) {
            super(ParserRules.SUM_2, node);
        }

        public Sum21 plusOrMinus() {
            return get(0, Sum21::new);
        }

        public Term term() {
            return get(1, Term::new);
        }
    }

    /**
     * '+' | '-'
     */
    public static final class Sum21 extends NodeWrapper {

        public Sum21(ParseTreeNode node) {
            super(ParserRules.SUM_2_1, node);
        }

        public boolean isPlus() {
            return is(0);
        }

        public boolean isMinus() {
            return is(1);
        }
    }
}
