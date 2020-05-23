package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * funcdef: 'def' ['func_type_hint'] ['func_args'] 'func_suite'
 */
public final class Funcdef extends NodeWrapper {

    public Funcdef(ParseTreeNode node) {
        super(ParserRules.FUNCDEF, node);
    }

    public FuncTypeHint funcTypeHint() {
        return get(1, FuncTypeHint::new);
    }

    public boolean hasFuncTypeHint() {
        return has(1);
    }

    public FuncArgs funcArgs() {
        return get(2, FuncArgs::new);
    }

    public boolean hasFuncArgs() {
        return has(2);
    }

    public FuncSuite funcSuite() {
        return get(3, FuncSuite::new);
    }
}
