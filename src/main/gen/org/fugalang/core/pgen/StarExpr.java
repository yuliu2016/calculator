package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;

// star_expr: '*' 'bitwise_or'
public final class StarExpr extends ConjunctionRule {
    public static final String RULE_NAME = "star_expr";

    private final boolean isTokenTimes;
    private final BitwiseOr bitwiseOr;

    public StarExpr(
            boolean isTokenTimes,
            BitwiseOr bitwiseOr
    ) {
        this.isTokenTimes = isTokenTimes;
        this.bitwiseOr = bitwiseOr;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenTimes", isTokenTimes);
        addRequired("bitwiseOr", bitwiseOr);
    }

    public boolean isTokenTimes() {
        return isTokenTimes;
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
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
