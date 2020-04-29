package org.fugalang.core.pgen;

// 'else' 'suite'
public class WhileStmt4Group {
    public final boolean isTokenElse;
    public final Suite suite;

    public WhileStmt4Group(
            boolean isTokenElse,
            Suite suite
    ) {
        this.isTokenElse = isTokenElse;
        this.suite = suite;
    }
}
