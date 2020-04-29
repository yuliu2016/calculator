package org.fugalang.core.pgen;

import java.util.List;

// pipe_expr: 'atom_expr' ('->' 'atom_expr')*
public class PipeExpr {
    public final AtomExpr atomExpr;
    public final List<PipeExpr2Group> pipeExpr2GroupList;

    public PipeExpr(
            AtomExpr atomExpr,
            List<PipeExpr2Group> pipeExpr2GroupList
    ) {
        this.atomExpr = atomExpr;
        this.pipeExpr2GroupList = pipeExpr2GroupList;
    }
}
