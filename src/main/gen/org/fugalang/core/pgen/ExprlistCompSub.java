package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * exprlist_comp_sub: 'exprlist_comp' | 'subscript'
 */
public final class ExprlistCompSub extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist_comp_sub", RuleType.Disjunction, true);

    public static ExprlistCompSub of(ParseTreeNode node) {
        return new ExprlistCompSub(node);
    }

    private ExprlistCompSub(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice("exprlistComp", exprlistComp());
        addChoice("subscript", subscript());
    }

    public ExprlistComp exprlistComp() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return ExprlistComp.of(element);
    }

    public boolean hasExprlistComp() {
        return exprlistComp() != null;
    }

    public Subscript subscript() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Subscript.of(element);
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
