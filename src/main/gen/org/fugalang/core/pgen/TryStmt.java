package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite')
public final class TryStmt extends ConjunctionRule {
    private final boolean isTokenTry;
    private final Suite suite;
    private final TryStmt3 tryStmt3;

    public TryStmt(
            boolean isTokenTry,
            Suite suite,
            TryStmt3 tryStmt3
    ) {
        this.isTokenTry = isTokenTry;
        this.suite = suite;
        this.tryStmt3 = tryStmt3;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenTry", isTokenTry);
        addRequired("suite", suite);
        addRequired("tryStmt3", tryStmt3);
    }

    public boolean isTokenTry() {
        return isTokenTry;
    }

    public Suite suite() {
        return suite;
    }

    public TryStmt3 tryStmt3() {
        return tryStmt3;
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite'
    public static final class TryStmt3 extends DisjunctionRule {
        private final TryStmt31 tryStmt31;
        private final TryStmt32 tryStmt32;

        public TryStmt3(
                TryStmt31 tryStmt31,
                TryStmt32 tryStmt32
        ) {
            this.tryStmt31 = tryStmt31;
            this.tryStmt32 = tryStmt32;
        }

        @Override
        protected void buildRule() {
            addChoice("tryStmt31", tryStmt31);
            addChoice("tryStmt32", tryStmt32);
        }

        public TryStmt31 tryStmt31() {
            return tryStmt31;
        }

        public TryStmt32 tryStmt32() {
            return tryStmt32;
        }
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite']
    public static final class TryStmt31 extends ConjunctionRule {
        private final TryStmt311 tryStmt311;
        private final List<TryStmt311> tryStmt311List;
        private final TryStmt312 tryStmt312;
        private final TryStmt313 tryStmt313;

        public TryStmt31(
                TryStmt311 tryStmt311,
                List<TryStmt311> tryStmt311List,
                TryStmt312 tryStmt312,
                TryStmt313 tryStmt313
        ) {
            this.tryStmt311 = tryStmt311;
            this.tryStmt311List = tryStmt311List;
            this.tryStmt312 = tryStmt312;
            this.tryStmt313 = tryStmt313;
        }

        @Override
        protected void buildRule() {
            addRequired("tryStmt311", tryStmt311);
            addRequired("tryStmt311List", tryStmt311List);
            addOptional("tryStmt312", tryStmt312);
            addOptional("tryStmt313", tryStmt313);
        }

        public TryStmt311 tryStmt311() {
            return tryStmt311;
        }

        public List<TryStmt311> tryStmt311List() {
            return tryStmt311List;
        }

        public Optional<TryStmt312> tryStmt312() {
            return Optional.ofNullable(tryStmt312);
        }

        public Optional<TryStmt313> tryStmt313() {
            return Optional.ofNullable(tryStmt313);
        }
    }

    // 'except_clause' 'suite'
    public static final class TryStmt311 extends ConjunctionRule {
        private final ExceptClause exceptClause;
        private final Suite suite;

        public TryStmt311(
                ExceptClause exceptClause,
                Suite suite
        ) {
            this.exceptClause = exceptClause;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("exceptClause", exceptClause);
            addRequired("suite", suite);
        }

        public ExceptClause exceptClause() {
            return exceptClause;
        }

        public Suite suite() {
            return suite;
        }
    }

    // 'else' 'suite'
    public static final class TryStmt312 extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public TryStmt312(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenElse", isTokenElse);
            addRequired("suite", suite);
        }

        public boolean isTokenElse() {
            return isTokenElse;
        }

        public Suite suite() {
            return suite;
        }
    }

    // 'finally' 'suite'
    public static final class TryStmt313 extends ConjunctionRule {
        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt313(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally);
            addRequired("suite", suite);
        }

        public boolean isTokenFinally() {
            return isTokenFinally;
        }

        public Suite suite() {
            return suite;
        }
    }

    // 'finally' 'suite'
    public static final class TryStmt32 extends ConjunctionRule {
        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt32(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally);
            addRequired("suite", suite);
        }

        public boolean isTokenFinally() {
            return isTokenFinally;
        }

        public Suite suite() {
            return suite;
        }
    }
}
