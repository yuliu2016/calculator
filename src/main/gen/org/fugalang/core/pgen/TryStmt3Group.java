package org.fugalang.core.pgen;

// ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite'
public class TryStmt3Group {
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
