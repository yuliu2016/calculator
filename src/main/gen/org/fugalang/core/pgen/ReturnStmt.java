package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("return_stmt", RuleType.Conjunction, true);

    public static ReturnStmt of(ParseTreeNode node) {
        return new ReturnStmt(node);
    }

    private ReturnStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenReturn(), "return");
        addOptional(exprlistStar());
    }

    public boolean isTokenReturn() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public ExprlistStar exprlistStar() {
        var element = getItem(1);
        element.failIfAbsent(ExprlistStar.RULE);
        return ExprlistStar.of(element);
    }

    public ExprlistStar exprlistStarOrNull() {
        var element = getItem(1);
        if (!element.isPresent(ExprlistStar.RULE)) {
            return null;
        }
        return ExprlistStar.of(element);
    }

    public boolean hasExprlistStar() {
        var element = getItem(1);
        return element.isPresent(ExprlistStar.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("return");
        if (result) ExprlistStar.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
