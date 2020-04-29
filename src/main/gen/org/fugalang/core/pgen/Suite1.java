package org.fugalang.core.pgen;

// ':' 'simple_stmt'
public class Suite1 {
    public final boolean isTokenColon;
    public final SimpleStmt simpleStmt;

    public Suite1(
            boolean isTokenColon,
            SimpleStmt simpleStmt
    ) {
        this.isTokenColon = isTokenColon;
        this.simpleStmt = simpleStmt;
    }
}
