package org.fugalang.core.pgen;

// single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
public class SingleInput {
    public final Object newline;
    public final SimpleStmt simpleStmt;
    public final SingleInput3 singleInput3;

    public SingleInput(
            Object newline,
            SimpleStmt simpleStmt,
            SingleInput3 singleInput3
    ) {
        this.newline = newline;
        this.simpleStmt = simpleStmt;
        this.singleInput3 = singleInput3;
    }

    // 'compound_stmt' 'NEWLINE'
    public static class SingleInput3 {
        public final CompoundStmt compoundStmt;
        public final Object newline;

        public SingleInput3(
                CompoundStmt compoundStmt,
                Object newline
        ) {
            this.compoundStmt = compoundStmt;
            this.newline = newline;
        }
    }
}
