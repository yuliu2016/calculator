package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_type_hint: '<' 'expr' '>'
 */
public final class FuncTypeHint extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("func_type_hint", RuleType.Conjunction);

    public static FuncTypeHint of(ParseTreeNode node) {
        return new FuncTypeHint(node);
    }

    private FuncTypeHint(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return get(1, Expr::of);
    }
}
