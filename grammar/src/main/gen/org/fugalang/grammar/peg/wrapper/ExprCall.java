package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * expr_call:
 * *   | expr_name '(' ','.expr_arg+ ')'
 */
public final class ExprCall extends NodeWrapper {

    public ExprCall(ParseTreeNode node) {
        super(node);
    }

    public ExprName exprName() {
        return new ExprName(get(0));
    }

    public List<ExprArg> exprArgs() {
        return getList(2, ExprArg::new);
    }
}
