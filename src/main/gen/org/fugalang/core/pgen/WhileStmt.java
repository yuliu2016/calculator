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

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public Suite suite() {
        return Suite.of(getItem(2));
    }

    public ElseSuite elseSuite() {
        return ElseSuite.of(getItem(3));
    }

    public boolean hasElseSuite() {
        return hasItemOfRule(3, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("while");
        r = r && NamedExpr.parse(t, l + 1);
        r = r && Suite.parse(t, l + 1);
        if (r) ElseSuite.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
