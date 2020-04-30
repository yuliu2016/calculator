package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// continue_stmt: 'continue'
public final class ContinueStmt extends ConjunctionRule {
    public static final String RULE_NAME = "continue_stmt";

    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenContinue", isTokenContinue);
    }

    public boolean isTokenContinue() {
        return isTokenContinue;
    }
}
