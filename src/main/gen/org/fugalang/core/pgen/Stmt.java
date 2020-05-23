package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
 */
public final class Stmt extends NodeWrapper {

    public Stmt(ParseTreeNode node) {
        super(ParserRules.STMT, node);
    }

    public Stmt1 simpleStmtOrCompoundStmt() {
        return get(0, Stmt1::new);
    }

    public String newline() {
        return get(1, TokenType.NEWLINE);
    }

    /**
     * 'simple_stmt' | 'compound_stmt'
     */
    public static final class Stmt1 extends NodeWrapper {

        public Stmt1(ParseTreeNode node) {
            super(ParserRules.STMT_1, node);
        }

        public SimpleStmt simpleStmt() {
            return get(0, SimpleStmt::new);
        }

        public boolean hasSimpleStmt() {
            return has(0, ParserRules.SIMPLE_STMT);
        }

        public CompoundStmt compoundStmt() {
            return get(1, CompoundStmt::new);
        }

        public boolean hasCompoundStmt() {
            return has(1, ParserRules.COMPOUND_STMT);
        }
    }
}
