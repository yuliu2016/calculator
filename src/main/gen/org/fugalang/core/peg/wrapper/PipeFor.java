package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * pipe_for: [comp_for] 'for' targetlist ['if' named_expr] [parameters | block_suite]
 */
public final class PipeFor extends NodeWrapper {

    public PipeFor(ParseTreeNode node) {
        super(node);
    }

    public CompFor compFor() {
        return new CompFor(get(0));
    }

    public boolean hasCompFor() {
        return has(0);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(2));
    }

    public PipeFor4 ifNamedExpr() {
        return new PipeFor4(get(3));
    }

    public boolean hasIfNamedExpr() {
        return has(3);
    }

    public PipeFor5 parametersOrBlockSuite() {
        return new PipeFor5(get(4));
    }

    public boolean hasParametersOrBlockSuite() {
        return has(4);
    }

    /**
     * 'if' named_expr
     */
    public static final class PipeFor4 extends NodeWrapper {

        public PipeFor4(ParseTreeNode node) {
            super(node);
        }

        public NamedExpr namedExpr() {
            return new NamedExpr(get(1));
        }
    }

    /**
     * parameters | block_suite
     */
    public static final class PipeFor5 extends NodeWrapper {

        public PipeFor5(ParseTreeNode node) {
            super(node);
        }

        public Parameters parameters() {
            return new Parameters(get(0));
        }

        public boolean hasParameters() {
            return has(0);
        }

        public BlockSuite blockSuite() {
            return new BlockSuite(get(1));
        }

        public boolean hasBlockSuite() {
            return has(1);
        }
    }
}
