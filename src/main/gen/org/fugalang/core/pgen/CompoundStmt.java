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
        return get(0, IfStmt::of);
    }

    public boolean hasIfStmt() {
        return has(0, IfStmt.RULE);
    }

    public WhileStmt whileStmt() {
        return get(1, WhileStmt::of);
    }

    public boolean hasWhileStmt() {
        return has(1, WhileStmt.RULE);
    }

    public ForStmt forStmt() {
        return get(2, ForStmt::of);
    }

    public boolean hasForStmt() {
        return has(2, ForStmt.RULE);
    }

    public TryStmt tryStmt() {
        return get(3, TryStmt::of);
    }

    public boolean hasTryStmt() {
        return has(3, TryStmt.RULE);
    }

    public WithStmt withStmt() {
        return get(4, WithStmt::of);
    }

    public boolean hasWithStmt() {
        return has(4, WithStmt.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
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
