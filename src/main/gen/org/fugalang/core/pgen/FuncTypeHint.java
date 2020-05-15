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
        return Expr.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("<");
        r = r && Expr.parse(t, lv + 1);
        r = r && t.consumeToken(">");
        t.exit(r);
        return r;
    }
}
