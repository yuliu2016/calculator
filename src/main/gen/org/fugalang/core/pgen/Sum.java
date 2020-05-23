package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * sum: 'term' ('sum_op' 'term')*
 */
public final class Sum extends NodeWrapper {

    public Sum(ParseTreeNode node) {
        super(ParserRules.SUM, node);
    }

    public Term term() {
        return get(0, Term.class);
    }

    public List<Sum2> sumOpTerms() {
        return getList(1, Sum2.class);
    }

    /**
     * 'sum_op' 'term'
     */
    public static final class Sum2 extends NodeWrapper {

        public Sum2(ParseTreeNode node) {
            super(ParserRules.SUM_2, node);
        }

        public SumOp sumOp() {
            return get(0, SumOp.class);
        }

        public Term term() {
            return get(1, Term.class);
        }
    }
}
