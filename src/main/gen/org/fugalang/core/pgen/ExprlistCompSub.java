package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// exprlist_comp_sub: 'exprlist_comp' | 'subscript'
public final class ExprlistCompSub extends DisjunctionRule {
    public static final String RULE_NAME = "exprlist_comp_sub";

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
        setExplicitName(RULE_NAME);
        addChoice("exprlistComp", exprlistComp);
        addChoice("subscript", subscript);
    }

    public ExprlistComp exprlistComp() {
        return exprlistComp;
    }

    public Subscript subscript() {
        return subscript;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }
}
