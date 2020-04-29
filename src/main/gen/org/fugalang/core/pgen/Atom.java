package org.fugalang.core.pgen;

// atom: 'compound_atom' | 'simple_atom'
public class Atom {
    private final CompoundAtom compoundAtom;
    private final SimpleAtom simpleAtom;

    public Atom(
            CompoundAtom compoundAtom,
            SimpleAtom simpleAtom
    ) {
        this.compoundAtom = compoundAtom;
        this.simpleAtom = simpleAtom;
    }

    public CompoundAtom getCompoundAtom() {
        return compoundAtom;
    }

    public SimpleAtom getSimpleAtom() {
        return simpleAtom;
    }
}
