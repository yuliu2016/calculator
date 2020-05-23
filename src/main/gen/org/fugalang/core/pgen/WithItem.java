package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;
import org.fugalang.core.token.TokenType;

/**
 * with_item: 'expr' ['as' 'NAME']
 */
public final class WithItem extends NodeWrapper {

    public WithItem(ParseTreeNode node) {
        super(FugaRules.WITH_ITEM, node);
    }

    public Expr expr() {
        return get(0, Expr.class);
    }

    public WithItem2 asName() {
        return get(1, WithItem2.class);
    }

    public boolean hasAsName() {
        return has(1);
    }

    /**
     * 'as' 'NAME'
     */
    public static final class WithItem2 extends NodeWrapper {

        public WithItem2(ParseTreeNode node) {
            super(FugaRules.WITH_ITEM_2, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
