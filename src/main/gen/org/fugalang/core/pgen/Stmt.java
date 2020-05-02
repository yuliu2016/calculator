package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addRequired("stmt1", stmt1());
        addRequired("newline", newline());
    }

    public Stmt1 stmt1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Stmt1.of(element);
    }

    public String newline() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Stmt1.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenType("NEWLINE");

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
            addChoice("simpleStmt", simpleStmt());
            addChoice("compoundStmt", compoundStmt());
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return SimpleStmt.of(element);
        }

        public boolean hasSimpleStmt() {
            return simpleStmt() != null;
        }

        public CompoundStmt compoundStmt() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return CompoundStmt.of(element);
        }

        public boolean hasCompoundStmt() {
            return compoundStmt() != null;
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
