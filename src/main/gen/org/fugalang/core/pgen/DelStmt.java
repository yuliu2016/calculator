package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// del_stmt: 'del' 'targets'
public final class DelStmt extends ConjunctionRule {
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
