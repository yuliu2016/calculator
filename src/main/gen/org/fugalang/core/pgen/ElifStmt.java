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

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public Suite suite() {
        return Suite.of(getItem(2));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("elif");
        r = r && NamedExpr.parse(t, l + 1);
        r = r && Suite.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}
