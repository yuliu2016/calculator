package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * for_stmt: 'for' 'targetlist' 'in' 'exprlist' 'suite' ['else_suite']
 */
public final class ForStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("for_stmt", RuleType.Conjunction);

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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("for");
        r = r && Targetlist.parse(t, lv + 1);
        r = r && t.consumeToken("in");
        r = r && Exprlist.parse(t, lv + 1);
        r = r && Suite.parse(t, lv + 1);
        if (r) ElseSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
