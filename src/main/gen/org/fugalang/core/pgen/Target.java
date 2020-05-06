package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * target: 'bitwise_or' | 'star_expr'
 */
public final class Target extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("target", RuleType.Disjunction, true);

    public static Target of(ParseTreeNode node) {
        return new Target(node);
    }

    private Target(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(bitwiseOrOrNull());
        addChoice(starExprOrNull());
    }

    public BitwiseOr bitwiseOr() {
        var element = getItem(0);
        element.failIfAbsent(BitwiseOr.RULE);
        return BitwiseOr.of(element);
    }

    public BitwiseOr bitwiseOrOrNull() {
        var element = getItem(0);
        if (!element.isPresent(BitwiseOr.RULE)) {
            return null;
        }
        return BitwiseOr.of(element);
    }

    public boolean hasBitwiseOr() {
        var element = getItem(0);
        return element.isPresent(BitwiseOr.RULE);
    }

    public StarExpr starExpr() {
        var element = getItem(1);
        element.failIfAbsent(StarExpr.RULE);
        return StarExpr.of(element);
    }

    public StarExpr starExprOrNull() {
        var element = getItem(1);
        if (!element.isPresent(StarExpr.RULE)) {
            return null;
        }
        return StarExpr.of(element);
    }

    public boolean hasStarExpr() {
        var element = getItem(1);
        return element.isPresent(StarExpr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseOr.parse(parseTree, level + 1);
        result = result || StarExpr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
