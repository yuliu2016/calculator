package org.fugalang.core.pgen;

// 'else' 'suite'
public class ForStmt6Group {
    public final boolean isTokenElse;
    public final Suite suite;

    public ForStmt6Group(
            boolean isTokenElse,
            Suite suite
    ) {
        this.isTokenElse = isTokenElse;
        this.suite = suite;
    }
}
