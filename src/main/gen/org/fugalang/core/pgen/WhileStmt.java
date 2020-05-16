package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
 */
public final class WhileStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("while_stmt", RuleType.Conjunction);

    public static WhileStmt of(ParseTreeNode node) {
        return new WhileStmt(node);
    }

    private WhileStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::of);
    }

    public Suite suite() {
        return get(2, Suite::of);
    }

    public ElseSuite elseSuite() {
        return get(3, ElseSuite::of);
    }

    public boolean hasElseSuite() {
        return has(3, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("while");
        r = r && NamedExpr.parse(t, lv + 1);
        r = r && Suite.parse(t, lv + 1);
        if (r) ElseSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
