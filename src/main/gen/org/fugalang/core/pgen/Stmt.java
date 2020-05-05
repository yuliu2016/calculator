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

    @Override
    protected void buildRule() {
        addRequired(stmt1());
        addRequired(newline());
    }

    public Stmt1 stmt1() {
        var element = getItem(0);
        element.failIfAbsent(Stmt1.RULE);
        return Stmt1.of(element);
    }

    public String newline() {
        var element = getItem(1);
        element.failIfAbsent(TokenType.NEWLINE);
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Stmt1.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(TokenType.NEWLINE);

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addChoice(simpleStmt());
            addChoice(compoundStmt());
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(0);
            if (!element.isPresent(SimpleStmt.RULE)) {
                return null;
            }
            return SimpleStmt.of(element);
        }

        public boolean hasSimpleStmt() {
            var element = getItem(0);
            return element.isPresent(SimpleStmt.RULE);
        }

        public CompoundStmt compoundStmt() {
            var element = getItem(1);
            if (!element.isPresent(CompoundStmt.RULE)) {
                return null;
            }
            return CompoundStmt.of(element);
        }

        public boolean hasCompoundStmt() {
            var element = getItem(1);
            return element.isPresent(CompoundStmt.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = SimpleStmt.parse(parseTree, level + 1);
            result = result || CompoundStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
