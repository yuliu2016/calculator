package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * del_stmt: 'del' 'targetlist'
 */
public final class DelStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("del_stmt", RuleType.Conjunction);

    public static DelStmt of(ParseTreeNode node) {
        return new DelStmt(node);
    }

    private DelStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist::of);
    }
}
