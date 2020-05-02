package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addChoice("blockSuite1", blockSuite1());
        addChoice("blockSuite2", blockSuite2());
    }

    public BlockSuite1 blockSuite1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return BlockSuite1.of(element);
    }

    public boolean hasBlockSuite1() {
        return blockSuite1() != null;
    }

    public BlockSuite2 blockSuite2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return BlockSuite2.of(element);
    }

    public boolean hasBlockSuite2() {
        return blockSuite2() != null;
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
            addRequired("isTokenLbrace", isTokenLbrace());
            addRequired("simpleStmt", simpleStmt());
            addRequired("isTokenRbrace", isTokenRbrace());
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return SimpleStmt.of(element);
        }

        public boolean isTokenRbrace() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            result = result && SimpleStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("}");

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
            addRequired("isTokenLbrace", isTokenLbrace());
            addRequired("newline", newline());
            addRequired("stmtList", stmtList());
            addRequired("isTokenRbrace", isTokenRbrace());
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Object newline() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return element.asObject();
        }

        public List<Stmt> stmtList() {
            if (stmtList != null) {
                return stmtList;
            }
            List<Stmt> result = null;
            var element = getItem(2);
            for (var node : element.asCollection()) {
                if (result == null) result = new ArrayList<>();
                result.add(Stmt.of(node));
            }
            stmtList = result == null ? Collections.emptyList() : result;
            return stmtList;
        }

        public boolean isTokenRbrace() {
            var element = getItem(3);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            result = result && parseTree.consumeTokenType("NEWLINE");
            parseTree.enterCollection();
            result = result && Stmt.parse(parseTree, level + 1);
            while (true) {
                var pos = parseTree.position();
                if (!Stmt.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = result && parseTree.consumeTokenLiteral("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
