package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {

    public WithStmt(ParseTreeNode node) {
        super(ParserRules.WITH_STMT, node);
    }

    public WithItem withItem() {
        return get(1, WithItem::new);
    }

    public List<WithStmt3> withItems() {
        return getList(2, WithStmt3::new);
    }

    public Suite suite() {
        return get(3, Suite::new);
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends NodeWrapper {

        public WithStmt3(ParseTreeNode node) {
            super(ParserRules.WITH_STMT_3, node);
        }

        public WithItem withItem() {
            return get(1, WithItem::new);
        }
    }
}
