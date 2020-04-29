package org.fugalang.core.pgen;

// del_stmt: 'del' 'targets'
public class DelStmt {
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
