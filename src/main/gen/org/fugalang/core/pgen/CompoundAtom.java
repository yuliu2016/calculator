package org.fugalang.core.pgen;

// compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
public class CompoundAtom {
    public final CompoundAtom1 compoundAtom1;
    public final CompoundAtom2 compoundAtom2;
    public final CompoundAtom3 compoundAtom3;

    public CompoundAtom(
            CompoundAtom1 compoundAtom1,
            CompoundAtom2 compoundAtom2,
            CompoundAtom3 compoundAtom3
    ) {
        this.compoundAtom1 = compoundAtom1;
        this.compoundAtom2 = compoundAtom2;
        this.compoundAtom3 = compoundAtom3;
    }
}
