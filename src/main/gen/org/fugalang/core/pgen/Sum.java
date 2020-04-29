package org.fugalang.core.pgen;

import java.util.List;

// sum: 'term' (('+' | '-') 'term')*
public class Sum {
    public final Term term;
    public final List<Sum2Group> sum2GroupList;

    public Sum(
            Term term,
            List<Sum2Group> sum2GroupList
    ) {
        this.term = term;
        this.sum2GroupList = sum2GroupList;
    }
}
