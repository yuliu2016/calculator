package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * term: 'pipeline' ('term_op' 'pipeline')*
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(ParserRules.TERM, node);
    }

    public Pipeline pipeline() {
        return get(0, Pipeline::new);
    }

    public List<Term2> termOpPipelines() {
        return getList(1, Term2::new);
    }

    /**
     * 'term_op' 'pipeline'
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(ParserRules.TERM_2, node);
        }

        public TermOp termOp() {
            return get(0, TermOp::new);
        }

        public Pipeline pipeline() {
            return get(1, Pipeline::new);
        }
    }
}
