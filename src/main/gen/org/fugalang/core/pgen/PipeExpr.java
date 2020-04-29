package org.fugalang.core.pgen;

import java.util.List;

// pipe_expr: 'atom_expr' ('->' 'atom_expr')*
public class PipeExpr {
    private final AtomExpr atomExpr;
    private final List<PipeExpr2Group> pipeExpr2GroupList;

    public PipeExpr(
            AtomExpr atomExpr,
            List<PipeExpr2Group> pipeExpr2GroupList
    ) {
        this.atomExpr = atomExpr;
        this.pipeExpr2GroupList = pipeExpr2GroupList;
    }

    public AtomExpr getAtomExpr() {
        return atomExpr;
    }

    public List<PipeExpr2Group> getPipeExpr2GroupList() {
        return pipeExpr2GroupList;
    }

    // '->' 'atom_expr'
    public static class PipeExpr2Group {
        private final boolean isTokenPipe;
        private final AtomExpr atomExpr;

        public PipeExpr2Group(
                boolean isTokenPipe,
                AtomExpr atomExpr
        ) {
            this.isTokenPipe = isTokenPipe;
            this.atomExpr = atomExpr;
        }

        public boolean getIsTokenPipe() {
            return isTokenPipe;
        }

        public AtomExpr getAtomExpr() {
            return atomExpr;
        }
    }
}
