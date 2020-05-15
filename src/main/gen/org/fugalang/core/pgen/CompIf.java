package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_if: 'if' 'named_expr' ['comp_iter']
 */
public final class CompIf extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comp_if", RuleType.Conjunction);

    public static CompIf of(ParseTreeNode node) {
        return new CompIf(node);
    }

    private CompIf(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public CompIter compIter() {
        return CompIter.of(getItem(2));
    }

    public boolean hasCompIter() {
        return hasItemOfRule(2, CompIter.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("if");
        r = r && NamedExpr.parse(t, lv + 1);
        if (r) CompIter.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
