package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * exprlist_comp_sub: 'exprlist_comp' | 'subscript'
 */
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
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = ExprlistComp.parse(parseTree, level + 1);
        result = result || Subscript.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
