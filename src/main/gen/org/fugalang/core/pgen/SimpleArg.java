package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: 'NAME' ['=' 'expr']
 */
public final class SimpleArg extends NodeWrapper {

    public SimpleArg(ParseTreeNode node) {
        super(ParserRules.SIMPLE_ARG, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SimpleArg2 expr() {
        return get(1, SimpleArg2::new);
    }

    public boolean hasExpr() {
        return has(1, ParserRules.SIMPLE_ARG_2);
    }

    /**
     * '=' 'expr'
     */
    public static final class SimpleArg2 extends NodeWrapper {

        public SimpleArg2(ParseTreeNode node) {
            super(ParserRules.SIMPLE_ARG_2, node);
        }

        public Expr expr() {
            return get(1, Expr::new);
        }
    }
}
