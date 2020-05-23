package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * nonlocal_stmt: 'nonlocal' 'NAME' (',' 'NAME')*
 */
public final class NonlocalStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("nonlocal_stmt", RuleType.Conjunction);

    public static NonlocalStmt of(ParseTreeNode node) {
        return new NonlocalStmt(node);
    }

    private NonlocalStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public List<NonlocalStmt3> names() {
        return getList(2, NonlocalStmt3::of);
    }

    /**
     * ',' 'NAME'
     */
    public static final class NonlocalStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("nonlocal_stmt:3", RuleType.Conjunction);

        public static NonlocalStmt3 of(ParseTreeNode node) {
            return new NonlocalStmt3(node);
        }

        private NonlocalStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
