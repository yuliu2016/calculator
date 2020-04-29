package org.fugalang.core.pgen;

import java.util.List;

// subscriptlist: 'subscript' (',' 'subscript')* [',']
public class Subscriptlist {
    private final Subscript subscript;
    private final List<Subscriptlist2Group> subscriptlist2GroupList;
    private final boolean isTokenComma;

    public Subscriptlist(
            Subscript subscript,
            List<Subscriptlist2Group> subscriptlist2GroupList,
            boolean isTokenComma
    ) {
        this.subscript = subscript;
        this.subscriptlist2GroupList = subscriptlist2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public Subscript getSubscript() {
        return subscript;
    }

    public List<Subscriptlist2Group> getSubscriptlist2GroupList() {
        return subscriptlist2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // ',' 'subscript'
    public static class Subscriptlist2Group {
        private final boolean isTokenComma;
        private final Subscript subscript;

        public Subscriptlist2Group(
                boolean isTokenComma,
                Subscript subscript
        ) {
            this.isTokenComma = isTokenComma;
            this.subscript = subscript;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Subscript getSubscript() {
            return subscript;
        }
    }
}
