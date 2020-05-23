package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * func_suite: ':' 'expr' | 'block_suite'
 */
public final class FuncSuite extends NodeWrapper {

    public FuncSuite(ParseTreeNode node) {
        super(ParserRules.FUNC_SUITE, node);
    }

    public FuncSuite1 expr() {
        return get(0, FuncSuite1::new);
    }

    public boolean hasExpr() {
        return has(0, ParserRules.FUNC_SUITE_1);
    }

    public BlockSuite blockSuite() {
        return get(1, BlockSuite::new);
    }

    public boolean hasBlockSuite() {
        return has(1, ParserRules.BLOCK_SUITE);
    }

    /**
     * ':' 'expr'
     */
    public static final class FuncSuite1 extends NodeWrapper {

        public FuncSuite1(ParseTreeNode node) {
            super(ParserRules.FUNC_SUITE_1, node);
        }

        public Expr expr() {
            return get(1, Expr::new);
        }
    }
}
