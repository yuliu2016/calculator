package org.fugalang.core.pgen;

import java.util.List;

// subscriptlist: 'subscript' (',' 'subscript')* [',']
public class Subscriptlist {
    public final Subscript subscript;
    public final List<Subscriptlist2Group> subscriptlist2GroupList;
    public final boolean isTokenComma;

    public Subscriptlist(
            Subscript subscript,
            List<Subscriptlist2Group> subscriptlist2GroupList,
            boolean isTokenComma
    ) {
        this.subscript = subscript;
        this.subscriptlist2GroupList = subscriptlist2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // ',' 'subscript'
    public static class Subscriptlist2Group {
        public final boolean isTokenComma;
        public final Subscript subscript;

        public Subscriptlist2Group(
                boolean isTokenComma,
                Subscript subscript
        ) {
            this.isTokenComma = isTokenComma;
            this.subscript = subscript;
        }
    }
}
