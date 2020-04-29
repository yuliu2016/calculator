package org.fugalang.core.pgen;

// comp_for: 'for' 'targets' 'in' 'disjunction' ['comp_iter']
public class CompFor {
    public final boolean isTokenFor;
    public final Targets targets;
    public final boolean isTokenIn;
    public final Disjunction disjunction;
    public final CompIter compIter;

    public CompFor(
            boolean isTokenFor,
            Targets targets,
            boolean isTokenIn,
            Disjunction disjunction,
            CompIter compIter
    ) {
        this.isTokenFor = isTokenFor;
        this.targets = targets;
        this.isTokenIn = isTokenIn;
        this.disjunction = disjunction;
        this.compIter = compIter;
    }
}
