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

    @Override
    protected void buildRule() {
        addRequired(isTokenTimes(), "*");
        addRequired(bitwiseOr());
    }

    public boolean isTokenTimes() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public BitwiseOr bitwiseOr() {
        var element = getItem(1);
        element.failIfAbsent(BitwiseOr.RULE);
        return BitwiseOr.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("*");
        result = result && BitwiseOr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
