package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
 */
public final class BlockSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("block_suite", RuleType.Disjunction, true);

    public static BlockSuite of(ParseTreeNode node) {
        return new BlockSuite(node);
    }

    private BlockSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(blockSuite1OrNull());
        addChoice(blockSuite2OrNull());
    }

    public BlockSuite1 blockSuite1() {
        var element = getItem(0);
        element.failIfAbsent(BlockSuite1.RULE);
        return BlockSuite1.of(element);
    }

    public BlockSuite1 blockSuite1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(BlockSuite1.RULE)) {
            return null;
        }
        return BlockSuite1.of(element);
    }

    public boolean hasBlockSuite1() {
        var element = getItem(0);
        return element.isPresent(BlockSuite1.RULE);
    }

    public BlockSuite2 blockSuite2() {
        var element = getItem(1);
        element.failIfAbsent(BlockSuite2.RULE);
        return BlockSuite2.of(element);
    }

    public BlockSuite2 blockSuite2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(BlockSuite2.RULE)) {
            return null;
        }
        return BlockSuite2.of(element);
    }

    public boolean hasBlockSuite2() {
        var element = getItem(1);
        return element.isPresent(BlockSuite2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BlockSuite1.parse(parseTree, level + 1);
        result = result || BlockSuite2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '{' 'simple_stmt' '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("block_suite:1", RuleType.Conjunction, false);

        public static BlockSuite1 of(ParseTreeNode node) {
            return new BlockSuite1(node);
        }

        private BlockSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenLbrace(), "{");
            addRequired(simpleStmt());
            addRequired(isTokenRbrace(), "}");
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(1);
            element.failIfAbsent(SimpleStmt.RULE);
            return SimpleStmt.of(element);
        }

        public boolean isTokenRbrace() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("{");
            result = result && SimpleStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("block_suite:2", RuleType.Conjunction, false);

        public static BlockSuite2 of(ParseTreeNode node) {
            return new BlockSuite2(node);
        }

        private BlockSuite2(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<Stmt> stmtList;

        @Override
        protected void buildRule() {
            addRequired(isTokenLbrace(), "{");
            addRequired(newline());
            addRequired(stmtList());
            addRequired(isTokenRbrace(), "}");
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String newline() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NEWLINE);
            return element.asString();
        }

        public List<Stmt> stmtList() {
            if (stmtList != null) {
                return stmtList;
            }
            List<Stmt> result = null;
            var element = getItem(2);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(Stmt.of(node));
            }
            stmtList = result == null ? Collections.emptyList() : result;
            return stmtList;
        }

        public boolean isTokenRbrace() {
            var element = getItem(3);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("{");
            result = result && parseTree.consumeToken(TokenType.NEWLINE);
            result = result && parseStmtList(parseTree, level + 1);
            result = result && parseTree.consumeToken("}");

            parseTree.exit(level, marker, result);
            return result;
        }

        private static boolean parseStmtList(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            parseTree.enterCollection();
            var result = Stmt.parse(parseTree, level + 1);
            if (result) while (true) {
                var pos = parseTree.position();
                if (!Stmt.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            return result;
        }
    }
}
