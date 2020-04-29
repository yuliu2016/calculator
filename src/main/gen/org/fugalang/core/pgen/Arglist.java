package org.fugalang.core.pgen;

// arglist: 'argument' (',' 'argument')* [',']
public class Arglist {
    public final Argument argument;
    public final Arglist2Group arglist2Group;
    public final boolean isTokenComma;

    public Arglist(
            Argument argument,
            Arglist2Group arglist2Group,
            boolean isTokenComma
    ) {
        this.argument = argument;
        this.arglist2Group = arglist2Group;
        this.isTokenComma = isTokenComma;
    }
}
