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

    public SmallStmt smallStmt() {
        return SmallStmt.of(getItem(0));
    }

    public List<SimpleStmt2> simpleStmt2List() {
        return getList(1, SimpleStmt2::of);
    }

    public boolean isTokenSemicolon() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SmallStmt.parse(parseTree, level + 1);
        if (result) parseSimpleStmt2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(";");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseSimpleStmt2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SimpleStmt2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
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

        public SmallStmt smallStmt() {
            return SmallStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(";");
            result = result && SmallStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
