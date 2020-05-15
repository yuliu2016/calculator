package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_type_hint: '<' 'expr' '>'
 */
public final class FuncTypeHint extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("func_type_hint", RuleType.Conjunction, true);

    public static FuncTypeHint of(ParseTreeNode node) {
        return new FuncTypeHint(node);
    }

    private FuncTypeHint(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("<");
        r = r && Expr.parse(t, l + 1);
        r = r && t.consumeToken(">");
        t.exit(r);
        return r;
    }
}
