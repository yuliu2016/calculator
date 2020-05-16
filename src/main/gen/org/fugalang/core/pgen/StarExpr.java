package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * star_expr: '*' 'bitwise_or'
 */
public final class StarExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("star_expr", RuleType.Conjunction);

    public static StarExpr of(ParseTreeNode node) {
        return new StarExpr(node);
    }

    private StarExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return get(1, BitwiseOr::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("*");
        r = r && BitwiseOr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
