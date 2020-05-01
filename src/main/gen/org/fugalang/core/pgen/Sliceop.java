package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// sliceop: ':' ['expr']
public final class Sliceop extends ConjunctionRule {
    public static final String RULE_NAME = "sliceop";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenColon", isTokenColon);
        addOptional("expr", expr);
    }

    public boolean isTokenColon() {
        return isTokenColon;
    }

    public Optional<Expr> expr() {
        return Optional.ofNullable(expr);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral(":");
        Expr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
