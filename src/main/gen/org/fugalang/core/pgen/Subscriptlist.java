package org.fugalang.core.pgen;

// subscriptlist: 'subscript' (',' 'subscript')* [',']
public class Subscriptlist {
    public final Subscript subscript;
    public final Subscriptlist2Group subscriptlist2Group;
    public final boolean isTokenComma;

    public Subscriptlist(
            Subscript subscript,
            Subscriptlist2Group subscriptlist2Group,
            boolean isTokenComma
    ) {
        this.subscript = subscript;
        this.subscriptlist2Group = subscriptlist2Group;
        this.isTokenComma = isTokenComma;
    }
}
