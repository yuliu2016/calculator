package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * expr_as_name: expr ['as' NAME]
 */
public final class ExprAsName extends NodeWrapper {

    public ExprAsName(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return get(0, Expr.class);
    }

    public ExprAsName2 asName() {
        return get(1, ExprAsName2.class);
    }

    public boolean hasAsName() {
        return has(1);
    }

    /**
     * 'as' NAME
     */
    public static final class ExprAsName2 extends NodeWrapper {

        public ExprAsName2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
