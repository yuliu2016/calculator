package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * for_stmt: 'for' 'targetlist' 'in' 'exprlist' 'suite' ['else_suite']
 */
public final class ForStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("for_stmt", RuleType.Conjunction, true);

    public static ForStmt of(ParseTreeNode node) {
        return new ForStmt(node);
    }

    private ForStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenFor(), "for");
        addRequired(targetlist());
        addRequired(isTokenIn(), "in");
        addRequired(exprlist());
        addRequired(suite());
        addOptional(elseSuiteOrNull());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targetlist targetlist() {
        var element = getItem(1);
        element.failIfAbsent(Targetlist.RULE);
        return Targetlist.of(element);
    }

    public boolean isTokenIn() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Exprlist exprlist() {
        var element = getItem(3);
        element.failIfAbsent(Exprlist.RULE);
        return Exprlist.of(element);
    }

    public Suite suite() {
        var element = getItem(4);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public ElseSuite elseSuite() {
        var element = getItem(5);
        element.failIfAbsent(ElseSuite.RULE);
        return ElseSuite.of(element);
    }

    public ElseSuite elseSuiteOrNull() {
        var element = getItem(5);
        if (!element.isPresent(ElseSuite.RULE)) {
            return null;
        }
        return ElseSuite.of(element);
    }

    public boolean hasElseSuite() {
        var element = getItem(5);
        return element.isPresent(ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Exprlist.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
