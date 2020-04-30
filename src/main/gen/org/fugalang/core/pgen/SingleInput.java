package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
public final class SingleInput extends DisjunctionRule {
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

        addChoice("newline", newline);
        addChoice("simpleStmt", simpleStmt);
        addChoice("singleInput3", singleInput3);
    }

    public Object newline() {
        return newline;
    }

    public SimpleStmt simpleStmt() {
        return simpleStmt;
    }

    public SingleInput3 singleInput3() {
        return singleInput3;
    }

    // 'compound_stmt' 'NEWLINE'
    public static final class SingleInput3 extends ConjunctionRule {
        private final CompoundStmt compoundStmt;
        private final Object newline;

        public SingleInput3(
                CompoundStmt compoundStmt,
                Object newline
        ) {
            this.compoundStmt = compoundStmt;
            this.newline = newline;

            addRequired("compoundStmt", compoundStmt);
            addRequired("newline", newline);
        }

        public CompoundStmt compoundStmt() {
            return compoundStmt;
        }

        public Object newline() {
            return newline;
        }
    }
}
