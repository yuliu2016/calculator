package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// subscriptlist: 'subscript' (',' 'subscript')* [',']
public final class Subscriptlist extends ConjunctionRule {
    private final Subscript subscript;
    private final List<Subscriptlist2> subscriptlist2List;
    private final boolean isTokenComma;

    public Subscriptlist(
            Subscript subscript,
            List<Subscriptlist2> subscriptlist2List,
            boolean isTokenComma
    ) {
        this.subscript = subscript;
        this.subscriptlist2List = subscriptlist2List;
        this.isTokenComma = isTokenComma;

        addRequired("subscript", subscript);
        addRequired("subscriptlist2List", subscriptlist2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Subscript subscript() {
        return subscript;
    }

    public List<Subscriptlist2> subscriptlist2List() {
        return subscriptlist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'subscript'
    public static final class Subscriptlist2 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Subscript subscript;

        public Subscriptlist2(
                boolean isTokenComma,
                Subscript subscript
        ) {
            this.isTokenComma = isTokenComma;
            this.subscript = subscript;

            addRequired("isTokenComma", isTokenComma);
            addRequired("subscript", subscript);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Subscript subscript() {
            return subscript;
        }
    }
}
