package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * elif_stmt: 'elif' 'named_expr' 'suite'
 */
public final class ElifStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("elif_stmt", RuleType.Conjunction);

    public static ElifStmt of(ParseTreeNode node) {
        return new ElifStmt(node);
    }

    private ElifStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::of);
    }

    public Suite suite() {
        return get(2, Suite::of);
    }
}
