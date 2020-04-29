package org.fugalang.core.pgen;

import java.util.List;

// arglist: 'argument' (',' 'argument')* [',']
public class Arglist {
    private final Argument argument;
    private final List<Arglist2Group> arglist2GroupList;
    private final boolean isTokenComma;

    public Arglist(
            Argument argument,
            List<Arglist2Group> arglist2GroupList,
            boolean isTokenComma
    ) {
        this.argument = argument;
        this.arglist2GroupList = arglist2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public Argument getArgument() {
        return argument;
    }

    public List<Arglist2Group> getArglist2GroupList() {
        return arglist2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // ',' 'argument'
    public static class Arglist2Group {
        private final boolean isTokenComma;
        private final Argument argument;

        public Arglist2Group(
                boolean isTokenComma,
                Argument argument
        ) {
            this.isTokenComma = isTokenComma;
            this.argument = argument;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Argument getArgument() {
            return argument;
        }
    }
}
