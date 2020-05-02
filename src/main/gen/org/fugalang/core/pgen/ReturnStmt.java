package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("return_stmt", RuleType.Conjunction, true);

    private final boolean isTokenReturn;
    private final ExprlistStar exprlistStar;

    public ReturnStmt(
            boolean isTokenReturn,
            ExprlistStar exprlistStar
    ) {
        this.isTokenReturn = isTokenReturn;
        this.exprlistStar = exprlistStar;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenReturn", isTokenReturn());
        addOptional("exprlistStar", exprlistStar());
    }

    public boolean isTokenReturn() {
        return isTokenReturn;
    }

    public ExprlistStar exprlistStar() {
        return exprlistStar;
    }

    public boolean hasExprlistStar() {
        return exprlistStar() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("return");
        ExprlistStar.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
