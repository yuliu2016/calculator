package org.fugalang.core.pgen;

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
}
