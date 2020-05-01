package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.Optional;

/**
 * comp_if: 'if' 'expr' ['comp_iter']
 */
public final class CompIf extends ConjunctionRule {
    public static final String RULE_NAME = "comp_if";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenIf", isTokenIf);
        addRequired("expr", expr);
        addOptional("compIter", compIter);
    }

    public boolean isTokenIf() {
        return isTokenIf;
    }

    public Expr expr() {
        return expr;
    }

    public Optional<CompIter> compIter() {
        return Optional.ofNullable(compIter);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("if");
        result = result && Expr.parse(parseTree, level + 1);
        CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
