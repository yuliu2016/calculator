package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * nonlocal_stmt: 'nonlocal' 'NAME' (',' 'NAME')*
 */
public final class NonlocalStmt extends NodeWrapper {

    public NonlocalStmt(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public List<NonlocalStmt3> names() {
        return getList(2, NonlocalStmt3.class);
    }

    /**
     * ',' 'NAME'
     */
    public static final class NonlocalStmt3 extends NodeWrapper {

        public NonlocalStmt3(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
