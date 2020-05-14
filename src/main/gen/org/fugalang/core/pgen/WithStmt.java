package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_stmt", RuleType.Conjunction, true);

    public static WithStmt of(ParseTreeNode node) {
        return new WithStmt(node);
    }

    private WithStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public WithItem withItem() {
        return WithItem.of(getItem(1));
    }

    public List<WithStmt3> withStmt3List() {
        return getList(2, WithStmt3::of);
    }

    public Suite suite() {
        return Suite.of(getItem(3));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("with");
        result = result && WithItem.parse(parseTree, level + 1);
        if (result) parseWithStmt3List(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseWithStmt3List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!WithStmt3.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_stmt:3", RuleType.Conjunction, false);

        public static WithStmt3 of(ParseTreeNode node) {
            return new WithStmt3(node);
        }

        private WithStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public WithItem withItem() {
            return WithItem.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && WithItem.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
