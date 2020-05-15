package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_if: 'if' 'expr' ['comp_iter']
 */
public final class CompIf extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_if", RuleType.Conjunction, true);

    public static CompIf of(ParseTreeNode node) {
        return new CompIf(node);
    }

    private CompIf(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public CompIter compIter() {
        return CompIter.of(getItem(2));
    }

    public boolean hasCompIter() {
        return hasItemOfRule(2, CompIter.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("if");
        r = r && Expr.parse(t, l + 1);
        if (r) CompIter.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
