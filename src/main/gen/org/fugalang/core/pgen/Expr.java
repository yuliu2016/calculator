package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr: 'conditional' | 'funcdef' | 'disjunction'
 */
public final class Expr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("expr", RuleType.Disjunction);

    public static Expr of(ParseTreeNode node) {
        return new Expr(node);
    }

    private Expr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Conditional conditional() {
        return get(0, Conditional::of);
    }

    public boolean hasConditional() {
        return has(0, Conditional.RULE);
    }

    public Funcdef funcdef() {
        return get(1, Funcdef::of);
    }

    public boolean hasFuncdef() {
        return has(1, Funcdef.RULE);
    }

    public Disjunction disjunction() {
        return get(2, Disjunction::of);
    }

    public boolean hasDisjunction() {
        return has(2, Disjunction.RULE);
    }
}
