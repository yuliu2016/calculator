package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
 */
public final class Stmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("stmt", RuleType.Conjunction, true);

    public static Stmt of(ParseTreeNode node) {
        return new Stmt(node);
    }

    private Stmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Stmt1 stmt1() {
        return Stmt1.of(getItem(0));
    }

    public String newline() {
        return getItemOfType(1, TokenType.NEWLINE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Stmt1.parse(t, l + 1);
        r = r && t.consumeToken(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * 'simple_stmt' | 'compound_stmt'
     */
    public static final class Stmt1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("stmt:1", RuleType.Disjunction, false);

        public static Stmt1 of(ParseTreeNode node) {
            return new Stmt1(node);
        }

        private Stmt1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(getItem(0));
        }

        public boolean hasSimpleStmt() {
            return hasItemOfRule(0, SimpleStmt.RULE);
        }

        public CompoundStmt compoundStmt() {
            return CompoundStmt.of(getItem(1));
        }

        public boolean hasCompoundStmt() {
            return hasItemOfRule(1, CompoundStmt.RULE);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = SimpleStmt.parse(t, l + 1);
            r = r || CompoundStmt.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
