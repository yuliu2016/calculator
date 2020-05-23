package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * term: pipeline (term_op pipeline)*
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(node);
    }

    public Pipeline pipeline() {
        return get(0, Pipeline.class);
    }

    public List<Term2> termOpPipelines() {
        return getList(1, Term2.class);
    }

    /**
     * term_op pipeline
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(node);
        }

        public TermOp termOp() {
            return get(0, TermOp.class);
        }

        public Pipeline pipeline() {
            return get(1, Pipeline.class);
        }
    }
}
