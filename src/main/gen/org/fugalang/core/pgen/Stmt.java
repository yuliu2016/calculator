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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Stmt1.parse(t, lv + 1);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = SimpleStmt.parse(t, lv + 1);
            r = r || CompoundStmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
