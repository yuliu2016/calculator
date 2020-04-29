package org.fugalang.core.pgen;

// atom: 'compound_atom' | 'simple_atom'
public class Atom {
    public final CompoundAtom compoundAtom;
    public final SimpleAtom simpleAtom;

    public Atom(
            CompoundAtom compoundAtom,
            SimpleAtom simpleAtom
    ) {
        this.compoundAtom = compoundAtom;
        this.simpleAtom = simpleAtom;
    }
}
