package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// pass_stmt: 'pass'
public final class PassStmt extends ConjunctionRule {
    public static final String RULE_NAME = "pass_stmt";

    private final boolean isTokenPass;

    public PassStmt(
            boolean isTokenPass
    ) {
        this.isTokenPass = isTokenPass;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenPass", isTokenPass);
    }

    public boolean isTokenPass() {
        return isTokenPass;
    }
}
