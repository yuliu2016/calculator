package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// atom_expr: ['await'] 'atom' 'trailer'*
public final class AtomExpr extends ConjunctionRule {
    private final boolean isTokenAwait;
    private final Atom atom;
    private final List<Trailer> trailerList;

    public AtomExpr(
            boolean isTokenAwait,
            Atom atom,
            List<Trailer> trailerList
    ) {
        this.isTokenAwait = isTokenAwait;
        this.atom = atom;
        this.trailerList = trailerList;

        addRequired("isTokenAwait", isTokenAwait);
        addRequired("atom", atom);
        addRequired("trailerList", trailerList);
    }

    public boolean getIsTokenAwait() {
        return isTokenAwait;
    }

    public Atom getAtom() {
        return atom;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }
}
