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

    public boolean getIsTokenDel() {
        return isTokenDel;
    }

    public Targets getTargets() {
        return targets;
    }
}
