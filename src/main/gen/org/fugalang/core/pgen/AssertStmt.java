package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * assert_stmt: 'assert' 'expr' [',' 'expr']
 */
public final class AssertStmt extends NodeWrapper {

    public AssertStmt(ParseTreeNode node) {
        super(ParserRules.ASSERT_STMT, node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public AssertStmt3 expr1() {
        return get(2, AssertStmt3.class);
    }

    public boolean hasExpr1() {
        return has(2);
    }

    /**
     * ',' 'expr'
     */
    public static final class AssertStmt3 extends NodeWrapper {

        public AssertStmt3(ParseTreeNode node) {
            super(ParserRules.ASSERT_STMT_3, node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}
