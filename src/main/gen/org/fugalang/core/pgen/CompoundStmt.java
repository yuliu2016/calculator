package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
 */
public final class CompoundStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("compound_stmt", RuleType.Disjunction);

    public static CompoundStmt of(ParseTreeNode node) {
        return new CompoundStmt(node);
    }

    private CompoundStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public IfStmt ifStmt() {
        return IfStmt.of(getItem(0));
    }

    public boolean hasIfStmt() {
        return hasItemOfRule(0, IfStmt.RULE);
    }

    public WhileStmt whileStmt() {
        return WhileStmt.of(getItem(1));
    }

    public boolean hasWhileStmt() {
        return hasItemOfRule(1, WhileStmt.RULE);
    }

    public ForStmt forStmt() {
        return ForStmt.of(getItem(2));
    }

    public boolean hasForStmt() {
        return hasItemOfRule(2, ForStmt.RULE);
    }

    public TryStmt tryStmt() {
        return TryStmt.of(getItem(3));
    }

    public boolean hasTryStmt() {
        return hasItemOfRule(3, TryStmt.RULE);
    }

    public WithStmt withStmt() {
        return WithStmt.of(getItem(4));
    }

    public boolean hasWithStmt() {
        return hasItemOfRule(4, WithStmt.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = IfStmt.parse(t, lv + 1);
        r = r || WhileStmt.parse(t, lv + 1);
        r = r || ForStmt.parse(t, lv + 1);
        r = r || TryStmt.parse(t, lv + 1);
        r = r || WithStmt.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
