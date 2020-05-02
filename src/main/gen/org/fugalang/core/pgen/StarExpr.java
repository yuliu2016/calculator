package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * star_expr: '*' 'bitwise_or'
 */
public final class StarExpr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("star_expr", RuleType.Conjunction, true);

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
        addRequired("isTokenTimes", isTokenTimes());
        addRequired("bitwiseOr", bitwiseOr());
    }

    public boolean isTokenTimes() {
        return isTokenTimes;
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("*");
        result = result && BitwiseOr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
