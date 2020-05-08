package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
 */
public final class WhileStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("while_stmt", RuleType.Conjunction, true);

    public static WhileStmt of(ParseTreeNode node) {
        return new WhileStmt(node);
    }

    private WhileStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenWhile(), "while");
        addRequired(namedExpr());
        addRequired(suite());
        addOptional(elseSuiteOrNull());
    }

    public boolean isTokenWhile() {
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

    public ElseSuite elseSuite() {
        var element = getItem(3);
        element.failIfAbsent(ElseSuite.RULE);
        return ElseSuite.of(element);
    }

    public ElseSuite elseSuiteOrNull() {
        var element = getItem(3);
        if (!element.isPresent(ElseSuite.RULE)) {
            return null;
        }
        return ElseSuite.of(element);
    }

    public boolean hasElseSuite() {
        var element = getItem(3);
        return element.isPresent(ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("while");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
