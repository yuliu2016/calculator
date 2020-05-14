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

    public Targetlist targetlist() {
        return Targetlist.of(getItem(1));
    }

    public Exprlist exprlist() {
        return Exprlist.of(getItem(3));
    }

    public Suite suite() {
        return Suite.of(getItem(4));
    }

    public ElseSuite elseSuite() {
        return ElseSuite.of(getItem(5));
    }

    public boolean hasElseSuite() {
        return hasItemOfRule(5, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Exprlist.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
