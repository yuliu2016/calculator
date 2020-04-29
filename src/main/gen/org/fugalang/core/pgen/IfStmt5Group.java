package org.fugalang.core.pgen;

// 'else' 'suite'
public class IfStmt5Group {
    public final boolean isTokenElse;
    public final Suite suite;

    public IfStmt5Group(
            boolean isTokenElse,
            Suite suite
    ) {
        this.isTokenElse = isTokenElse;
        this.suite = suite;
    }
}
