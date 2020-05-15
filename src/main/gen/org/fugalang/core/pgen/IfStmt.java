package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * if_stmt: 'if' 'named_expr' 'suite' 'elif_stmt'* ['else_suite']
 */
public final class IfStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("if_stmt", RuleType.Conjunction);

    public static IfStmt of(ParseTreeNode node) {
        return new IfStmt(node);
    }

    private IfStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public Suite suite() {
        return Suite.of(getItem(2));
    }

    public List<ElifStmt> elifStmtList() {
        return getList(3, ElifStmt::of);
    }

    public ElseSuite elseSuite() {
        return ElseSuite.of(getItem(4));
    }

    public boolean hasElseSuite() {
        return hasItemOfRule(4, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("if");
        r = r && NamedExpr.parse(t, lv + 1);
        r = r && Suite.parse(t, lv + 1);
        if (r) parseElifStmtList(t, lv);
        if (r) ElseSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseElifStmtList(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ElifStmt.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }
}
