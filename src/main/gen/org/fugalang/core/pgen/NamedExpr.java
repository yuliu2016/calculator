package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: 'NAME' ':=' 'expr' | 'expr'
 */
public final class NamedExpr extends NodeWrapper {

    public NamedExpr(ParseTreeNode node) {
        super(ParserRules.NAMED_EXPR, node);
    }

    public NamedExpr1 nameExpr() {
        return get(0, NamedExpr1::new);
    }

    public boolean hasNameExpr() {
        return has(0);
    }

    public Expr expr() {
        return get(1, Expr::new);
    }

    public boolean hasExpr() {
        return has(1);
    }

    /**
     * 'NAME' ':=' 'expr'
     */
    public static final class NamedExpr1 extends NodeWrapper {

        public NamedExpr1(ParseTreeNode node) {
            super(ParserRules.NAMED_EXPR_1, node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return get(2, Expr::new);
        }
    }
}
