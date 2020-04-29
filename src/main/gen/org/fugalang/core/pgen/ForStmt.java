package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// for_stmt: 'for' 'targets' 'in' 'exprlist' 'suite' ['else' 'suite']
public final class ForStmt extends ConjunctionRule {
    private final boolean isTokenFor;
    private final Targets targets;
    private final boolean isTokenIn;
    private final Exprlist exprlist;
    private final Suite suite;
    private final ForStmt6Group forStmt6Group;

    public ForStmt(
            boolean isTokenFor,
            Targets targets,
            boolean isTokenIn,
            Exprlist exprlist,
            Suite suite,
            ForStmt6Group forStmt6Group
    ) {
        this.isTokenFor = isTokenFor;
        this.targets = targets;
        this.isTokenIn = isTokenIn;
        this.exprlist = exprlist;
        this.suite = suite;
        this.forStmt6Group = forStmt6Group;
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

    public Exprlist getExprlist() {
        return exprlist;
    }

    public Suite getSuite() {
        return suite;
    }

    public Optional<ForStmt6Group> getForStmt6Group() {
        return Optional.ofNullable(forStmt6Group);
    }

    // 'else' 'suite'
    public static final class ForStmt6Group extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public ForStmt6Group(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }

        public boolean getIsTokenElse() {
            return isTokenElse;
        }

        public Suite getSuite() {
            return suite;
        }
    }
}
