package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * star_expr: '*' 'bitwise_or'
 */
public final class StarExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("star_expr", RuleType.Conjunction, true);

    public static StarExpr of(ParseTreeNode node) {
        return new StarExpr(node);
    }

    private StarExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return BitwiseOr.of(getItem(1));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("*");
        r = r && BitwiseOr.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
