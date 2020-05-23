package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * default_arg: 'typed_arg' ['=' 'expr']
 */
public final class DefaultArg extends NodeWrapper {

    public DefaultArg(ParseTreeNode node) {
        super(ParserRules.DEFAULT_ARG, node);
    }

    public TypedArg typedArg() {
        return get(0, TypedArg.class);
    }

    public DefaultArg2 expr() {
        return get(1, DefaultArg2.class);
    }

    public boolean hasExpr() {
        return has(1);
    }

    /**
     * '=' 'expr'
     */
    public static final class DefaultArg2 extends NodeWrapper {

        public DefaultArg2(ParseTreeNode node) {
            super(ParserRules.DEFAULT_ARG_2, node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}
