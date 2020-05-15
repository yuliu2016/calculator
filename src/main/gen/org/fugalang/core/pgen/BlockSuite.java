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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = BlockSuite1.parse(t, l + 1);
        r = r || BlockSuite2.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("{");
            r = r && SimpleStmt.parse(t, l + 1);
            r = r && t.consumeToken("}");
            t.exit(r);
            return r;
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

        public String newline() {
            return getItemOfType(1, TokenType.NEWLINE);
        }

        public List<Stmt> stmtList() {
            return getList(2, Stmt::of);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("{");
            r = r && t.consumeToken(TokenType.NEWLINE);
            r = r && parseStmtList(t, l);
            r = r && t.consumeToken("}");
            t.exit(r);
            return r;
        }

        private static boolean parseStmtList(ParseTree t, int l) {
            t.enterCollection();
            var r = Stmt.parse(t, l + 1);
            if (r) while (true) {
                var p = t.position();
                if (!Stmt.parse(t, l + 1)) break;
                if (t.guardLoopExit(p)) break;
            }
            t.exitCollection();
            return r;
        }
    }
}
