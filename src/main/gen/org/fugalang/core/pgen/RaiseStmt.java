package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * raise_stmt: 'raise' ['expr' ['from' 'expr']]
 */
public final class RaiseStmt extends NodeWrapper {

    public RaiseStmt(ParseTreeNode node) {
        super(ParserRules.RAISE_STMT, node);
    }

    public RaiseStmt2 raiseStmt2() {
        return get(1, RaiseStmt2::new);
    }

    public boolean hasRaiseStmt2() {
        return has(1, ParserRules.RAISE_STMT_2);
    }

    /**
     * 'expr' ['from' 'expr']
     */
    public static final class RaiseStmt2 extends NodeWrapper {

        public RaiseStmt2(ParseTreeNode node) {
            super(ParserRules.RAISE_STMT_2, node);
        }

        public Expr expr() {
            return get(0, Expr::new);
        }

        public RaiseStmt22 fromExpr() {
            return get(1, RaiseStmt22::new);
        }

        public boolean hasFromExpr() {
            return has(1, ParserRules.RAISE_STMT_2_2);
        }
    }

    /**
     * 'from' 'expr'
     */
    public static final class RaiseStmt22 extends NodeWrapper {

        public RaiseStmt22(ParseTreeNode node) {
            super(ParserRules.RAISE_STMT_2_2, node);
        }

        public Expr expr() {
            return get(1, Expr::new);
        }
    }
}
