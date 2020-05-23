package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * except_clause: 'except' ['expr' ['as' 'NAME']]
 */
public final class ExceptClause extends NodeWrapper {

    public ExceptClause(ParseTreeNode node) {
        super(ParserRules.EXCEPT_CLAUSE, node);
    }

    public ExceptClause2 exceptClause2() {
        return get(1, ExceptClause2.class);
    }

    public boolean hasExceptClause2() {
        return has(1);
    }

    /**
     * 'expr' ['as' 'NAME']
     */
    public static final class ExceptClause2 extends NodeWrapper {

        public ExceptClause2(ParseTreeNode node) {
            super(ParserRules.EXCEPT_CLAUSE_2, node);
        }

        public Expr expr() {
            return get(0, Expr.class);
        }

        public ExceptClause22 asName() {
            return get(1, ExceptClause22.class);
        }

        public boolean hasAsName() {
            return has(1);
        }
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ExceptClause22 extends NodeWrapper {

        public ExceptClause22(ParseTreeNode node) {
            super(ParserRules.EXCEPT_CLAUSE_2_2, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
