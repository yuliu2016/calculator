package org.fugalang.core.pgen;

// del_stmt: 'del' 'targets'
public class DelStmt {
    public final boolean isTokenDel;
    public final Targets targets;

    public DelStmt(
            boolean isTokenDel,
            Targets targets
    ) {
        this.isTokenDel = isTokenDel;
        this.targets = targets;
    }
}
