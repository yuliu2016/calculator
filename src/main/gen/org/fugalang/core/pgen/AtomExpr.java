package org.fugalang.core.pgen;

// atom_expr: ['await'] 'atom' 'trailer'*
public class AtomExpr {
    private final boolean isTokenAwait;
    private final Atom atom;
    private final Trailer trailer;

    public AtomExpr(
            boolean isTokenAwait,
            Atom atom,
            Trailer trailer
    ) {
        this.isTokenAwait = isTokenAwait;
        this.atom = atom;
        this.trailer = trailer;
    }

    public boolean getIsTokenAwait() {
        return isTokenAwait;
    }

    public Atom getAtom() {
        return atom;
    }

    public Trailer getTrailer() {
        return trailer;
    }
}
