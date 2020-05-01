package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
 */
public final class SingleInput extends DisjunctionRule {
    public static final String RULE_NAME = "single_input";

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

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("newline", newline);
        addChoice("simpleStmt", simpleStmt);
        addChoice("singleInput3", singleInput3);
    }

    public Object newline() {
        return newline;
    }

    public boolean hasNewline() {
        return newline() != null;
    }

    public SimpleStmt simpleStmt() {
        return simpleStmt;
    }

    public boolean hasSimpleStmt() {
        return simpleStmt() != null;
    }

    public SingleInput3 singleInput3() {
        return singleInput3;
    }

    public boolean hasSingleInput3() {
        return singleInput3() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenType("NEWLINE");
        result = result || SimpleStmt.parse(parseTree, level + 1);
        result = result || SingleInput3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'compound_stmt' 'NEWLINE'
     */
    public static final class SingleInput3 extends ConjunctionRule {
        public static final String RULE_NAME = "single_input:3";

        private final CompoundStmt compoundStmt;
        private final Object newline;

        public SingleInput3(
                CompoundStmt compoundStmt,
                Object newline
        ) {
            this.compoundStmt = compoundStmt;
            this.newline = newline;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("compoundStmt", compoundStmt);
            addRequired("newline", newline);
        }

        public CompoundStmt compoundStmt() {
            return compoundStmt;
        }

        public Object newline() {
            return newline;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = CompoundStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenType("NEWLINE");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
