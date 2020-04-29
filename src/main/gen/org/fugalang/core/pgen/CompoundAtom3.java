package org.fugalang.core.pgen;

// '{' ['dictorsetmaker'] '}'
public class CompoundAtom3 {
    public final boolean isTokenLbrace;
    public final Dictorsetmaker dictorsetmaker;
    public final boolean isTokenRbrace;

    public CompoundAtom3(
            boolean isTokenLbrace,
            Dictorsetmaker dictorsetmaker,
            boolean isTokenRbrace
    ) {
        this.isTokenLbrace = isTokenLbrace;
        this.dictorsetmaker = dictorsetmaker;
        this.isTokenRbrace = isTokenRbrace;
    }
}
