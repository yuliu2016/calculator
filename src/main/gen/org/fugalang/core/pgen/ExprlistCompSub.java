package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * exprlist_comp_sub: 'exprlist_comp' | 'subscript'
 */
public final class ExprlistCompSub extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("exprlist_comp_sub", RuleType.Disjunction, true);

    private final ExprlistComp exprlistComp;
    private final Subscript subscript;

    public ExprlistCompSub(
            ExprlistComp exprlistComp,
            Subscript subscript
    ) {
        this.exprlistComp = exprlistComp;
        this.subscript = subscript;
    }

    @Override
    protected void buildRule() {
        addChoice("exprlistComp", exprlistComp());
        addChoice("subscript", subscript());
    }

    public ExprlistComp exprlistComp() {
        return exprlistComp;
    }

    public boolean hasExprlistComp() {
        return exprlistComp() != null;
    }

    public Subscript subscript() {
        return subscript;
    }

    public boolean hasSubscript() {
        return subscript() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprlistComp.parse(parseTree, level + 1);
        result = result || Subscript.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
