package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// del_stmt: 'del' 'targets'
public final class DelStmt extends ConjunctionRule {
    public static final String RULE_NAME = "del_stmt";

    private final boolean isTokenDel;
    private final Targets targets;

    public DelStmt(
            boolean isTokenDel,
            Targets targets
    ) {
        this.isTokenDel = isTokenDel;
        this.targets = targets;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenDel", isTokenDel);
        addRequired("targets", targets);
    }

    public boolean isTokenDel() {
        return isTokenDel;
    }

    public Targets targets() {
        return targets;
    }
}
