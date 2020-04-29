package org.fugalang.core.pgen;

import java.util.List;

// term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
public class Term {
    public final Factor factor;
    public final List<Term2Group> term2GroupList;

    public Term(
            Factor factor,
            List<Term2Group> term2GroupList
    ) {
        this.factor = factor;
        this.term2GroupList = term2GroupList;
    }
}
