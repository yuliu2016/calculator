package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// conjunction: 'inversion' ('and' 'inversion')*
public final class Conjunction extends ConjunctionRule {
    private final Inversion inversion;
    private final List<Conjunction2Group> conjunction2GroupList;

    public Conjunction(
            Inversion inversion,
            List<Conjunction2Group> conjunction2GroupList
    ) {
        this.inversion = inversion;
        this.conjunction2GroupList = conjunction2GroupList;
    }

    public Inversion getInversion() {
        return inversion;
    }

    public List<Conjunction2Group> getConjunction2GroupList() {
        return conjunction2GroupList;
    }

    // 'and' 'inversion'
    public static final class Conjunction2Group extends ConjunctionRule {
        private final boolean isTokenAnd;
        private final Inversion inversion;

        public Conjunction2Group(
                boolean isTokenAnd,
                Inversion inversion
        ) {
            this.isTokenAnd = isTokenAnd;
            this.inversion = inversion;
        }

        public boolean getIsTokenAnd() {
            return isTokenAnd;
        }

        public Inversion getInversion() {
            return inversion;
        }
    }
}
