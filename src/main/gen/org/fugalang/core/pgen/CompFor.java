package org.fugalang.core.pgen;

import java.util.Optional;

// comp_for: 'for' 'targets' 'in' 'disjunction' ['comp_iter']
public class CompFor {
    private final boolean isTokenFor;
    private final Targets targets;
    private final boolean isTokenIn;
    private final Disjunction disjunction;
    private final CompIter compIter;

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

    public boolean getIsTokenFor() {
        return isTokenFor;
    }

    public Targets getTargets() {
        return targets;
    }

    public boolean getIsTokenIn() {
        return isTokenIn;
    }

    public Disjunction getDisjunction() {
        return disjunction;
    }

    public Optional<CompIter> getCompIter() {
        return Optional.ofNullable(compIter);
    }
}
