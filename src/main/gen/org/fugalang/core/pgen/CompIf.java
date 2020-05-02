package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_if: 'if' 'expr' ['comp_iter']
 */
public final class CompIf extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("comp_if", RuleType.Conjunction, true);

    private final boolean isTokenIf;
    private final Expr expr;
    private final CompIter compIter;

    public CompIf(
            boolean isTokenIf,
            Expr expr,
            CompIter compIter
    ) {
        this.isTokenIf = isTokenIf;
        this.expr = expr;
        this.compIter = compIter;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenIf", isTokenIf());
        addRequired("expr", expr());
        addOptional("compIter", compIter());
    }

    public boolean isTokenIf() {
        return isTokenIf;
    }

    public Expr expr() {
        return expr;
    }

    public CompIter compIter() {
        return compIter;
    }

    public boolean hasCompIter() {
        return compIter() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("if");
        result = result && Expr.parse(parseTree, level + 1);
        CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
