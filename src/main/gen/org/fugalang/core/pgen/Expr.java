package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * expr: 'conditional' | 'funcdef' | 'disjunction'
 */
public final class Expr extends NodeWrapper {

    public Expr(ParseTreeNode node) {
        super(ParserRules.EXPR, node);
    }

    public Conditional conditional() {
        return get(0, Conditional::new);
    }

    public boolean hasConditional() {
        return has(0, ParserRules.CONDITIONAL);
    }

    public Funcdef funcdef() {
        return get(1, Funcdef::new);
    }

    public boolean hasFuncdef() {
        return has(1, ParserRules.FUNCDEF);
    }

    public Disjunction disjunction() {
        return get(2, Disjunction::new);
    }

    public boolean hasDisjunction() {
        return has(2, ParserRules.DISJUNCTION);
    }
}
