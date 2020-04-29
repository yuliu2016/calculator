package org.fugalang.core.pgen;

// 'finally' 'suite'
public class TryStmt3Group2 {
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
