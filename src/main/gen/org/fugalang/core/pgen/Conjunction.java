package org.fugalang.core.pgen;

import java.util.List;

// conjunction: 'inversion' ('and' 'inversion')*
public class Conjunction {
    public final Inversion inversion;
    public final List<Conjunction2Group> conjunction2GroupList;

    public Conjunction(
            Inversion inversion,
            List<Conjunction2Group> conjunction2GroupList
    ) {
        this.inversion = inversion;
        this.conjunction2GroupList = conjunction2GroupList;
    }

    // 'and' 'inversion'
    public static class Conjunction2Group {
        public final boolean isTokenAnd;
        public final Inversion inversion;

        public Conjunction2Group(
                boolean isTokenAnd,
                Inversion inversion
        ) {
            this.isTokenAnd = isTokenAnd;
            this.inversion = inversion;
        }
    }
}
