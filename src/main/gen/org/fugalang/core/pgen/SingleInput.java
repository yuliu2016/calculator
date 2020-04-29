package org.fugalang.core.pgen;

// single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
public class SingleInput {
    private final Object newline;
    private final SimpleStmt simpleStmt;
    private final SingleInput3 singleInput3;

    public SingleInput(
            Object newline,
            SimpleStmt simpleStmt,
            SingleInput3 singleInput3
    ) {
        this.newline = newline;
        this.simpleStmt = simpleStmt;
        this.singleInput3 = singleInput3;
    }

    public Object getNewline() {
        return newline;
    }

    public SimpleStmt getSimpleStmt() {
        return simpleStmt;
    }

    public SingleInput3 getSingleInput3() {
        return singleInput3;
    }

    // 'compound_stmt' 'NEWLINE'
    public static class SingleInput3 {
        private final CompoundStmt compoundStmt;
        private final Object newline;

        public SingleInput3(
                CompoundStmt compoundStmt,
                Object newline
        ) {
            this.compoundStmt = compoundStmt;
            this.newline = newline;
        }

        public CompoundStmt getCompoundStmt() {
            return compoundStmt;
        }

        public Object getNewline() {
            return newline;
        }
    }
}
