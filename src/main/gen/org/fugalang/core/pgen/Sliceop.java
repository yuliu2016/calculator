package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * sliceop: ':' ['expr']
 */
public final class Sliceop extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("sliceop", RuleType.Conjunction, true);

    private final boolean isTokenColon;
    private final Expr expr;

    public Sliceop(
            boolean isTokenColon,
            Expr expr
    ) {
        this.isTokenColon = isTokenColon;
        this.expr = expr;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenColon", isTokenColon());
        addOptional("expr", expr());
    }

    public boolean isTokenColon() {
        return isTokenColon;
    }

    public Expr expr() {
        return expr;
    }

    public boolean hasExpr() {
        return expr() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral(":");
        Expr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
