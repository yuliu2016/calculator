package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

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

    public BlockSuite1 blockSuite1() {
        return BlockSuite1.of(getItem(0));
    }

    public boolean hasBlockSuite1() {
        return hasItemOfRule(0, BlockSuite1.RULE);
    }

    public BlockSuite2 blockSuite2() {
        return BlockSuite2.of(getItem(1));
    }

    public boolean hasBlockSuite2() {
        return hasItemOfRule(1, BlockSuite2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public boolean isTokenLbrace() {
            return true;
        }

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(getItem(1));
        }

        public boolean isTokenRbrace() {
            return true;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public boolean isTokenLbrace() {
            return true;
        }

        public String newline() {
            return getItemOfType(1,TokenType.NEWLINE);
        }

        public List<Stmt> stmtList() {
            return getList(2, Stmt::of);
        }

        public boolean isTokenRbrace() {
            return true;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
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
