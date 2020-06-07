package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * func_suite:
 * *   | ':' expr
 * *   | block_suite
 */
public final class FuncSuite extends NodeWrapper {

    public FuncSuite(ParseTreeNode node) {
        super(node);
    }

    public FuncSuite1 colonExpr() {
        return new FuncSuite1(get(0));
    }

    public boolean hasColonExpr() {
        return has(0);
    }

    public BlockSuite blockSuite() {
        return new BlockSuite(get(1));
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
            return new Expr(get(1));
        }
    }
}
