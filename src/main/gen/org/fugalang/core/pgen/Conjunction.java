package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// conjunction: 'inversion' ('and' 'inversion')*
public final class Conjunction extends ConjunctionRule {
    private final Inversion inversion;
    private final List<Conjunction2> conjunction2List;

    public Conjunction(
            Inversion inversion,
            List<Conjunction2> conjunction2List
    ) {
        this.inversion = inversion;
        this.conjunction2List = conjunction2List;

        addRequired("inversion", inversion);
        addRequired("conjunction2List", conjunction2List);
    }

    public Inversion inversion() {
        return inversion;
    }

    public List<Conjunction2> conjunction2List() {
        return conjunction2List;
    }

    // 'and' 'inversion'
    public static final class Conjunction2 extends ConjunctionRule {
        private final boolean isTokenAnd;
        private final Inversion inversion;

        public Conjunction2(
                boolean isTokenAnd,
                Inversion inversion
        ) {
            this.isTokenAnd = isTokenAnd;
            this.inversion = inversion;

            addRequired("isTokenAnd", isTokenAnd);
            addRequired("inversion", inversion);
        }

        public boolean isTokenAnd() {
            return isTokenAnd;
        }

        public Inversion inversion() {
            return inversion;
        }
    }
}
