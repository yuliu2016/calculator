package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
public final class BlockSuite extends DisjunctionRule {
    public static final String RULE_NAME = "block_suite";

    private final BlockSuite1 blockSuite1;
    private final BlockSuite2 blockSuite2;

    public BlockSuite(
            BlockSuite1 blockSuite1,
            BlockSuite2 blockSuite2
    ) {
        this.blockSuite1 = blockSuite1;
        this.blockSuite2 = blockSuite2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("blockSuite1", blockSuite1);
        addChoice("blockSuite2", blockSuite2);
    }

    public BlockSuite1 blockSuite1() {
        return blockSuite1;
    }

    public BlockSuite2 blockSuite2() {
        return blockSuite2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = BlockSuite1.parse(parseTree, level + 1);
        if (!result) result = BlockSuite2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // '{' 'simple_stmt' '}'
    public static final class BlockSuite1 extends ConjunctionRule {
        public static final String RULE_NAME = "block_suite:1";

        private final boolean isTokenLbrace;
        private final SimpleStmt simpleStmt;
        private final boolean isTokenRbrace;

        public BlockSuite1(
                boolean isTokenLbrace,
                SimpleStmt simpleStmt,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.simpleStmt = simpleStmt;
            this.isTokenRbrace = isTokenRbrace;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLbrace", isTokenLbrace);
            addRequired("simpleStmt", simpleStmt);
            addRequired("isTokenRbrace", isTokenRbrace);
        }

        public boolean isTokenLbrace() {
            return isTokenLbrace;
        }

        public SimpleStmt simpleStmt() {
            return simpleStmt;
        }

        public boolean isTokenRbrace() {
            return isTokenRbrace;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            result = result && SimpleStmt.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '{' 'NEWLINE' 'stmt'+ '}'
    public static final class BlockSuite2 extends ConjunctionRule {
        public static final String RULE_NAME = "block_suite:2";

        private final boolean isTokenLbrace;
        private final Object newline;
        private final Stmt stmt;
        private final List<Stmt> stmtList;
        private final boolean isTokenRbrace;

        public BlockSuite2(
                boolean isTokenLbrace,
                Object newline,
                Stmt stmt,
                List<Stmt> stmtList,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.newline = newline;
            this.stmt = stmt;
            this.stmtList = stmtList;
            this.isTokenRbrace = isTokenRbrace;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLbrace", isTokenLbrace);
            addRequired("newline", newline);
            addRequired("stmt", stmt);
            addRequired("stmtList", stmtList);
            addRequired("isTokenRbrace", isTokenRbrace);
        }

        public boolean isTokenLbrace() {
            return isTokenLbrace;
        }

        public Object newline() {
            return newline;
        }

        public Stmt stmt() {
            return stmt;
        }

        public List<Stmt> stmtList() {
            return stmtList;
        }

        public boolean isTokenRbrace() {
            return isTokenRbrace;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            result = result && parseTree.consumeTokenType("NEWLINE");
            result = result && Stmt.parse(parseTree, level + 1);
            while (true) {
                if (!Stmt.parse(parseTree, level + 1)) {
                    break;
                }
            }
            result = result && parseTree.consumeTokenLiteral("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
