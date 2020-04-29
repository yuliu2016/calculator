package org.fugalang.core.pgen;

import java.util.List;

// arglist: 'argument' (',' 'argument')* [',']
public class Arglist {
    public final Argument argument;
    public final List<Arglist2Group> arglist2GroupList;
    public final boolean isTokenComma;

    public Arglist(
            Argument argument,
            List<Arglist2Group> arglist2GroupList,
            boolean isTokenComma
    ) {
        this.argument = argument;
        this.arglist2GroupList = arglist2GroupList;
        this.isTokenComma = isTokenComma;
    }
}
