package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * func_suite: ':' expr | block_suite
 */
public final class FuncSuite extends NodeWrapper {

    public FuncSuite(ParseTreeNode node) {
        super(node);
    }

    public FuncSuite1 colonExpr() {
        return get(0, FuncSuite1.class);
    }

    public boolean hasColonExpr() {
        return has(0);
    }

    public BlockSuite blockSuite() {
        return get(1, BlockSuite.class);
    }

    public boolean hasBlockSuite() {
        return has(1);
    }

    /**
     * ':' expr
     */
    public static final class FuncSuite1 extends NodeWrapper {

        public FuncSuite1(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}
