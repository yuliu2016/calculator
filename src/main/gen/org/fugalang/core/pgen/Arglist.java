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

    // ',' 'argument'
    public static class Arglist2Group {
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
}
