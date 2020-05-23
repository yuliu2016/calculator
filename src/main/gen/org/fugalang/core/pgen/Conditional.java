package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * conditional: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
 */
public final class Conditional extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("conditional", RuleType.Conjunction);

    public static Conditional of(ParseTreeNode node) {
        return new Conditional(node);
    }

    private Conditional(ParseTreeNode node) {
        super(RULE, node);
    }

    public Disjunction disjunction() {
        return get(1, Disjunction::of);
    }

    public Disjunction disjunction1() {
        return get(3, Disjunction::of);
    }

    public Expr expr() {
        return get(5, Expr::of);
    }
}
