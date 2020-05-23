package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ['if' 'named_expr'] ['parameters' | 'block_suite']
 */
public final class PipeFor extends NodeWrapper {

    public PipeFor(ParseTreeNode node) {
        super(ParserRules.PIPE_FOR, node);
    }

    public CompFor compFor() {
        return get(0, CompFor.class);
    }

    public boolean hasCompFor() {
        return has(0);
    }

    public Targetlist targetlist() {
        return get(2, Targetlist.class);
    }

    public PipeFor4 ifNamedExpr() {
        return get(3, PipeFor4.class);
    }

    public boolean hasIfNamedExpr() {
        return has(3);
    }

    public PipeFor5 parametersOrBlockSuite() {
        return get(4, PipeFor5.class);
    }

    public boolean hasParametersOrBlockSuite() {
        return has(4);
    }

    /**
     * 'if' 'named_expr'
     */
    public static final class PipeFor4 extends NodeWrapper {

        public PipeFor4(ParseTreeNode node) {
            super(ParserRules.PIPE_FOR_4, node);
        }

        public NamedExpr namedExpr() {
            return get(1, NamedExpr.class);
        }
    }

    /**
     * 'parameters' | 'block_suite'
     */
    public static final class PipeFor5 extends NodeWrapper {

        public PipeFor5(ParseTreeNode node) {
            super(ParserRules.PIPE_FOR_5, node);
        }

        public Parameters parameters() {
            return get(0, Parameters.class);
        }

        public boolean hasParameters() {
            return has(0);
        }

        public BlockSuite blockSuite() {
            return get(1, BlockSuite.class);
        }

        public boolean hasBlockSuite() {
            return has(1);
        }
    }
}
