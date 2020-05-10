package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * elif_stmt: 'elif' 'named_expr' 'suite'
 */
public final class ElifStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("elif_stmt", RuleType.Conjunction, true);

    public static ElifStmt of(ParseTreeNode node) {
        return new ElifStmt(node);
    }

    private ElifStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenElif(), "elif");
        addRequired(namedExpr());
        addRequired(suite());
    }

    public boolean isTokenElif() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public NamedExpr namedExpr() {
        var element = getItem(1);
        element.failIfAbsent(NamedExpr.RULE);
        return NamedExpr.of(element);
    }

    public Suite suite() {
        var element = getItem(2);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("elif");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
