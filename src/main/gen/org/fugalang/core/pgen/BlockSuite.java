package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
 */
public final class BlockSuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("block_suite", RuleType.Disjunction);

    public static BlockSuite of(ParseTreeNode node) {
        return new BlockSuite(node);
    }

    private BlockSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public BlockSuite1 simpleStmt() {
        return get(0, BlockSuite1::of);
    }

    public boolean hasSimpleStmt() {
        return has(0, BlockSuite1.RULE);
    }

    public BlockSuite2 blockSuite2() {
        return get(1, BlockSuite2::of);
    }

    public boolean hasBlockSuite2() {
        return has(1, BlockSuite2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BlockSuite1.parse(t, lv + 1);
        r = r || BlockSuite2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '{' 'simple_stmt' '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("block_suite:1", RuleType.Conjunction);

        public static BlockSuite1 of(ParseTreeNode node) {
            return new BlockSuite1(node);
        }

        private BlockSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return get(1, SimpleStmt::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("{");
            r = r && SimpleStmt.parse(t, lv + 1);
            r = r && t.consume("}");
            t.exit(r);
            return r;
        }
    }

    /**
     * '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("block_suite:2", RuleType.Conjunction);

        public static BlockSuite2 of(ParseTreeNode node) {
            return new BlockSuite2(node);
        }

        private BlockSuite2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public List<Stmt> stmts() {
            return getList(2, Stmt::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("{");
            r = r && t.consume(TokenType.NEWLINE);
            r = r && parseStmts(t, lv);
            r = r && t.consume("}");
            t.exit(r);
            return r;
        }

        private static boolean parseStmts(ParseTree t, int lv) {
            t.enterCollection();
            var r = Stmt.parse(t, lv + 1);
            if (r) while (true) {
                var p = t.position();
                if (!Stmt.parse(t, lv + 1) || t.loopGuard(p)) break;
            }
            t.exitCollection();
            return r;
        }
    }
}
