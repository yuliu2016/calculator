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
        return get(1, Targetlist::of);
    }

    public Exprlist exprlist() {
        return get(3, Exprlist::of);
    }

    public Suite suite() {
        return get(4, Suite::of);
    }

    public ElseSuite elseSuite() {
        return get(5, ElseSuite::of);
    }

    public boolean hasElseSuite() {
        return has(5, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("for");
        r = r && Targetlist.parse(t, lv + 1);
        r = r && t.consume("in");
        r = r && Exprlist.parse(t, lv + 1);
        r = r && Suite.parse(t, lv + 1);
        if (r) ElseSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
