package org.fugalang.core.pgen;

// atom_expr: ['await'] 'atom' 'trailer'*
public class AtomExpr {
    public final boolean isTokenAwait;
    public final Atom atom;
    public final Trailer trailer;

    public AtomExpr(
            boolean isTokenAwait,
            Atom atom,
            Trailer trailer
    ) {
        this.isTokenAwait = isTokenAwait;
        this.atom = atom;
        this.trailer = trailer;
    }
}
