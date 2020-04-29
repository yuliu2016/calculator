package org.fugalang.core.pgen;

// '->' 'atom_expr'
public class PipeExpr2Group {
    public final boolean isTokenPipe;
    public final AtomExpr atomExpr;

    public PipeExpr2Group(
            boolean isTokenPipe,
            AtomExpr atomExpr
    ) {
        this.isTokenPipe = isTokenPipe;
        this.atomExpr = atomExpr;
    }
}
