package org.fugalang.core.pgen;

// pipe_expr: 'atom_expr' ('->' 'atom_expr')*
public class PipeExpr {
    public final AtomExpr atomExpr;
    public final PipeExpr2Group pipeExpr2Group;

    public PipeExpr(
            AtomExpr atomExpr,
            PipeExpr2Group pipeExpr2Group
    ) {
        this.atomExpr = atomExpr;
        this.pipeExpr2Group = pipeExpr2Group;
    }
}
