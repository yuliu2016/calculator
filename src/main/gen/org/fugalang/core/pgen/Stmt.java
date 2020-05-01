package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
 */
public final class Stmt extends ConjunctionRule {
    public static final String RULE_NAME = "stmt";

    private final Stmt1 stmt1;
    private final Object newline;

    public Stmt(
            Stmt1 stmt1,
            Object newline
    ) {
        this.stmt1 = stmt1;
        this.newline = newline;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("stmt1", stmt1);
        addRequired("newline", newline);
    }

    public Stmt1 stmt1() {
        return stmt1;
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

        result = Stmt1.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenType("NEWLINE");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'simple_stmt' | 'compound_stmt'
     */
    public static final class Stmt1 extends DisjunctionRule {
        public static final String RULE_NAME = "stmt:1";

        private final SimpleStmt simpleStmt;
        private final CompoundStmt compoundStmt;

        public Stmt1(
                SimpleStmt simpleStmt,
                CompoundStmt compoundStmt
        ) {
            this.simpleStmt = simpleStmt;
            this.compoundStmt = compoundStmt;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("simpleStmt", simpleStmt);
            addChoice("compoundStmt", compoundStmt);
        }

        public SimpleStmt simpleStmt() {
            return simpleStmt;
        }

        public boolean hasSimpleStmt() {
            return simpleStmt() != null;
        }

        public CompoundStmt compoundStmt() {
            return compoundStmt;
        }

        public boolean hasCompoundStmt() {
            return compoundStmt() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = SimpleStmt.parse(parseTree, level + 1);
            result = result || CompoundStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
