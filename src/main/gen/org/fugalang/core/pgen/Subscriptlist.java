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
}
