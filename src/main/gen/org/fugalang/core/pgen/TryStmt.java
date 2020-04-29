package org.fugalang.core.pgen;

import java.util.List;

// try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite')
public class TryStmt {
    public final boolean isTokenTry;
    public final Suite suite;
    public final TryStmt3Group tryStmt3Group;

    public TryStmt(
            boolean isTokenTry,
            Suite suite,
            TryStmt3Group tryStmt3Group
    ) {
        this.isTokenTry = isTokenTry;
        this.suite = suite;
        this.tryStmt3Group = tryStmt3Group;
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite'
    public static class TryStmt3Group {
        public final TryStmt3Group1 tryStmt3Group1;
        public final TryStmt3Group2 tryStmt3Group2;

        public TryStmt3Group(
                TryStmt3Group1 tryStmt3Group1,
                TryStmt3Group2 tryStmt3Group2
        ) {
            this.tryStmt3Group1 = tryStmt3Group1;
            this.tryStmt3Group2 = tryStmt3Group2;
        }
    }

    // ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite']
    public static class TryStmt3Group1 {
        public final TryStmt3Group11Group tryStmt3Group11Group;
        public final List<TryStmt3Group11Group> tryStmt3Group11GroupList;
        public final TryStmt3Group12Group tryStmt3Group12Group;
        public final TryStmt3Group13Group tryStmt3Group13Group;

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
        }
    }

    // 'except_clause' 'suite'
    public static class TryStmt3Group11Group {
        public final ExceptClause exceptClause;
        public final Suite suite;

        public TryStmt3Group11Group(
                ExceptClause exceptClause,
                Suite suite
        ) {
            this.exceptClause = exceptClause;
            this.suite = suite;
        }
    }

    // 'else' 'suite'
    public static class TryStmt3Group12Group {
        public final boolean isTokenElse;
        public final Suite suite;

        public TryStmt3Group12Group(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }
    }

    // 'finally' 'suite'
    public static class TryStmt3Group13Group {
        public final boolean isTokenFinally;
        public final Suite suite;

        public TryStmt3Group13Group(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }
    }

    // 'finally' 'suite'
    public static class TryStmt3Group2 {
        public final boolean isTokenFinally;
        public final Suite suite;

        public TryStmt3Group2(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }
    }
}
