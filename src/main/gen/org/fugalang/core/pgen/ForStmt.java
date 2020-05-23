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
}
