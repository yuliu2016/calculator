package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// break_stmt: 'break'
public final class BreakStmt extends ConjunctionRule {
    public static final String RULE_NAME = "break_stmt";

    private final boolean isTokenBreak;

    public BreakStmt(
            boolean isTokenBreak
    ) {
        this.isTokenBreak = isTokenBreak;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenBreak", isTokenBreak);
    }

    public boolean isTokenBreak() {
        return isTokenBreak;
    }
}
