package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// return_stmt: 'return' ['exprlist_star']
public final class ReturnStmt extends ConjunctionRule {
    public static final String RULE_NAME = "return_stmt";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenReturn", isTokenReturn);
        addOptional("exprlistStar", exprlistStar);
    }

    public boolean isTokenReturn() {
        return isTokenReturn;
    }

    public Optional<ExprlistStar> exprlistStar() {
        return Optional.ofNullable(exprlistStar);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("return");
        ExprlistStar.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
