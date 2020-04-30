package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
public final class BlockSuite extends DisjunctionRule {
    private final BlockSuite1 blockSuite1;
    private final BlockSuite2 blockSuite2;

    public BlockSuite(
            BlockSuite1 blockSuite1,
            BlockSuite2 blockSuite2
    ) {
        this.blockSuite1 = blockSuite1;
        this.blockSuite2 = blockSuite2;
    }

    public BlockSuite1 getBlockSuite1() {
        return blockSuite1;
    }

    public BlockSuite2 getBlockSuite2() {
        return blockSuite2;
    }

    // '{' 'simple_stmt' '}'
    public static final class BlockSuite1 extends ConjunctionRule {
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

        public boolean getIsTokenLbrace() {
            return isTokenLbrace;
        }

        public SimpleStmt getSimpleStmt() {
            return simpleStmt;
        }

        public boolean getIsTokenRbrace() {
            return isTokenRbrace;
        }
    }

    // '{' 'NEWLINE' 'stmt'+ '}'
    public static final class BlockSuite2 extends ConjunctionRule {
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

        public boolean getIsTokenLbrace() {
            return isTokenLbrace;
        }

        public Object getNewline() {
            return newline;
        }

        public Stmt getStmt() {
            return stmt;
        }

        public List<Stmt> getStmtList() {
            return stmtList;
        }

        public boolean getIsTokenRbrace() {
            return isTokenRbrace;
        }
    }
}
