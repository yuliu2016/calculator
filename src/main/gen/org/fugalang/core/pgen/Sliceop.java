package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * sliceop: ':' ['expr']
 */
public final class Sliceop extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("sliceop", RuleType.Conjunction, true);

    public static Sliceop of(ParseTreeNode node) {
        return new Sliceop(node);
    }

    private Sliceop(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenColon", isTokenColon());
        addOptional("expr", expr());
    }

    public boolean isTokenColon() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Expr expr() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Expr.of(element);
    }

    public boolean hasExpr() {
        return expr() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral(":");
        Expr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}