package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * typed_arg: 'NAME' [':' 'expr']
 */
public final class TypedArg extends NodeWrapper {

    public TypedArg(ParseTreeNode node) {
        super(ParserRules.TYPED_ARG, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public TypedArg2 expr() {
        return get(1, TypedArg2::new);
    }

    public boolean hasExpr() {
        return has(1, ParserRules.TYPED_ARG_2);
    }

    /**
     * ':' 'expr'
     */
    public static final class TypedArg2 extends NodeWrapper {

        public TypedArg2(ParseTreeNode node) {
            super(ParserRules.TYPED_ARG_2, node);
        }

        public Expr expr() {
            return get(1, Expr::new);
        }
    }
}
