package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * expr_call:
 * *   | expr_name '(' [','.expr_arg+ [',']] ')'
 */
public final class ExprCall extends NodeWrapper {

    public ExprCall(ParseTreeNode node) {
        super(node);
    }

    public ExprName exprName() {
        return new ExprName(get(0));
    }

    public ExprCall3 exprCall3() {
        return new ExprCall3(get(2));
    }

    public boolean hasExprCall3() {
        return has(2);
    }

    /**
     * ','.expr_arg+ [',']
     */
    public static final class ExprCall3 extends NodeWrapper {

        public ExprCall3(ParseTreeNode node) {
            super(node);
        }

        public List<ExprArg> exprArgs() {
            return getList(0, ExprArg::new);
        }

        public boolean isComma() {
            return is(1);
        }
    }
}
