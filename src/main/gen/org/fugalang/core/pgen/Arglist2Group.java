package org.fugalang.core.pgen;

// ',' 'argument'
public class Arglist2Group {
    public final boolean isTokenComma;
    public final Argument argument;

    public Arglist2Group(
            boolean isTokenComma,
            Argument argument
    ) {
        this.isTokenComma = isTokenComma;
        this.argument = argument;
    }
}
