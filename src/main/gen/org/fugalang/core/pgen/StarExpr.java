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
}
