package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_stmt", RuleType.Conjunction, true);

    public static SimpleStmt of(ParseTreeNode node) {
        return new SimpleStmt(node);
    }

    private SimpleStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<SimpleStmt2> simpleStmt2List;

    @Override
    protected void buildRule() {
        addRequired("smallStmt", smallStmt());
        addRequired("simpleStmt2List", simpleStmt2List());
        addRequired("isTokenSemicolon", isTokenSemicolon());
    }

    public SmallStmt smallStmt() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return SmallStmt.of(element);
    }

    public List<SimpleStmt2> simpleStmt2List() {
        return simpleStmt2List;
    }

    public boolean isTokenSemicolon() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SmallStmt.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SimpleStmt2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(";");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_stmt:2", RuleType.Conjunction, false);

        public static SimpleStmt2 of(ParseTreeNode node) {
            return new SimpleStmt2(node);
        }

        private SimpleStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenSemicolon", isTokenSemicolon());
            addRequired("smallStmt", smallStmt());
        }

        public boolean isTokenSemicolon() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public SmallStmt smallStmt() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return SmallStmt.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(";");
            result = result && SmallStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
