package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * funcdef:
 * *   | 'def' [func_type_hint] [func_args] func_suite
 */
public final class Funcdef extends NodeWrapper {

    public Funcdef(ParseTreeNode node) {
        super(node);
    }

    public FuncTypeHint funcTypeHint() {
        return new FuncTypeHint(get(1));
    }

    public boolean hasFuncTypeHint() {
        return has(1);
    }

    public FuncArgs funcArgs() {
        return new FuncArgs(get(2));
    }

    public boolean hasFuncArgs() {
        return has(2);
    }

    public FuncSuite funcSuite() {
        return new FuncSuite(get(3));
    }
}
