package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite')
public final class TryStmt extends ConjunctionRule {
    private final boolean isTokenTry;
    private final Suite suite;
    private final TryStmt3Group tryStmt3Group;

    public TryStmt(
            boolean isTokenTry,
            Suite suite,
            TryStmt3Group tryStmt3Group
    ) {
        this.isTokenTry = isTokenTry;
        this.suite = suite;
        this.tryStmt3Group = tryStmt3Group;

        addRequired("isTokenTry", isTokenTry);
        addRequired("suite", suite);
        addRequired("tryStmt3Group", tryStmt3Group);
    }

    public boolean getIsTokenTry() {
        return isTokenTry;
    }

    public Suite getSuite() {
        return suite;
    }

    public TryStmt3Group getTryStmt3Group() {
        return tryStmt3Group;
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite'
    public static final class TryStmt3Group extends DisjunctionRule {
        private final TryStmt3Group1 tryStmt3Group1;
        private final TryStmt3Group2 tryStmt3Group2;

        public TryStmt3Group(
                TryStmt3Group1 tryStmt3Group1,
                TryStmt3Group2 tryStmt3Group2
        ) {
            this.tryStmt3Group1 = tryStmt3Group1;
            this.tryStmt3Group2 = tryStmt3Group2;

            addChoice("tryStmt3Group1", tryStmt3Group1);
            addChoice("tryStmt3Group2", tryStmt3Group2);
        }

        public TryStmt3Group1 getTryStmt3Group1() {
            return tryStmt3Group1;
        }

        public TryStmt3Group2 getTryStmt3Group2() {
            return tryStmt3Group2;
        }
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite']
    public static final class TryStmt3Group1 extends ConjunctionRule {
        private final TryStmt3Group11Group tryStmt3Group11Group;
        private final List<TryStmt3Group11Group> tryStmt3Group11GroupList;
        private final TryStmt3Group12Group tryStmt3Group12Group;
        private final TryStmt3Group13Group tryStmt3Group13Group;

        public TryStmt3Group1(
                TryStmt3Group11Group tryStmt3Group11Group,
                List<TryStmt3Group11Group> tryStmt3Group11GroupList,
                TryStmt3Group12Group tryStmt3Group12Group,
                TryStmt3Group13Group tryStmt3Group13Group
        ) {
            this.tryStmt3Group11Group = tryStmt3Group11Group;
            this.tryStmt3Group11GroupList = tryStmt3Group11GroupList;
            this.tryStmt3Group12Group = tryStmt3Group12Group;
            this.tryStmt3Group13Group = tryStmt3Group13Group;

            addRequired("tryStmt3Group11Group", tryStmt3Group11Group);
            addRequired("tryStmt3Group11GroupList", tryStmt3Group11GroupList);
            addOptional("tryStmt3Group12Group", tryStmt3Group12Group);
            addOptional("tryStmt3Group13Group", tryStmt3Group13Group);
        }

        public TryStmt3Group11Group getTryStmt3Group11Group() {
            return tryStmt3Group11Group;
        }

        public List<TryStmt3Group11Group> getTryStmt3Group11GroupList() {
            return tryStmt3Group11GroupList;
        }

        public Optional<TryStmt3Group12Group> getTryStmt3Group12Group() {
            return Optional.ofNullable(tryStmt3Group12Group);
        }

        public Optional<TryStmt3Group13Group> getTryStmt3Group13Group() {
            return Optional.ofNullable(tryStmt3Group13Group);
        }
    }

    // 'except_clause' 'suite'
    public static final class TryStmt3Group11Group extends ConjunctionRule {
        private final ExceptClause exceptClause;
        private final Suite suite;

        public TryStmt3Group11Group(
                ExceptClause exceptClause,
                Suite suite
        ) {
            this.exceptClause = exceptClause;
            this.suite = suite;

            addRequired("exceptClause", exceptClause);
            addRequired("suite", suite);
        }

        public ExceptClause getExceptClause() {
            return exceptClause;
        }

        public Suite getSuite() {
            return suite;
        }
    }

    // 'else' 'suite'
    public static final class TryStmt3Group12Group extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public TryStmt3Group12Group(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;

            addRequired("isTokenElse", isTokenElse);
            addRequired("suite", suite);
        }

        public boolean getIsTokenElse() {
            return isTokenElse;
        }

        public Suite getSuite() {
            return suite;
        }
    }

    // 'finally' 'suite'
    public static final class TryStmt3Group13Group extends ConjunctionRule {
        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt3Group13Group(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;

            addRequired("isTokenFinally", isTokenFinally);
            addRequired("suite", suite);
        }

        public boolean getIsTokenFinally() {
            return isTokenFinally;
        }

        public Suite getSuite() {
            return suite;
        }
    }

    // 'finally' 'suite'
    public static final class TryStmt3Group2 extends ConjunctionRule {
        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt3Group2(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;

            addRequired("isTokenFinally", isTokenFinally);
            addRequired("suite", suite);
        }

        public boolean getIsTokenFinally() {
            return isTokenFinally;
        }

        public Suite getSuite() {
            return suite;
        }
    }
}
