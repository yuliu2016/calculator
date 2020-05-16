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
        return get(1, NamedExpr::of);
    }

    public Suite suite() {
        return get(2, Suite::of);
    }

    public List<ElifStmt> elifStmts() {
        return getList(3, ElifStmt::of);
    }

    public ElseSuite elseSuite() {
        return get(4, ElseSuite::of);
    }

    public boolean hasElseSuite() {
        return has(4, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("if");
        r = r && NamedExpr.parse(t, lv + 1);
        r = r && Suite.parse(t, lv + 1);
        if (r) parseElifStmts(t, lv);
        if (r) ElseSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseElifStmts(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ElifStmt.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }
}
