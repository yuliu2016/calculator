package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
 */
public final class Stmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("stmt", RuleType.Conjunction);

    public static Stmt of(ParseTreeNode node) {
        return new Stmt(node);
    }

    private Stmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Stmt1 simpleStmtOrCompoundStmt() {
        return get(0, Stmt1::of);
    }

    public String newline() {
        return get(1, TokenType.NEWLINE);
    }

    /**
     * 'simple_stmt' | 'compound_stmt'
     */
    public static final class Stmt1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("stmt:1", RuleType.Disjunction);

        public static Stmt1 of(ParseTreeNode node) {
            return new Stmt1(node);
        }

        private Stmt1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return get(0, SimpleStmt::of);
        }

        public boolean hasSimpleStmt() {
            return has(0, SimpleStmt.RULE);
        }

        public CompoundStmt compoundStmt() {
            return get(1, CompoundStmt::of);
        }

        public boolean hasCompoundStmt() {
            return has(1, CompoundStmt.RULE);
        }
    }
}
