package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * with_stmt: 'with' with_item (',' with_item)* suite
 */
public final class WithStmt extends NodeWrapper {

    public WithStmt(ParseTreeNode node) {
        super(node);
    }

    public WithItem withItem() {
        return get(1, WithItem.class);
    }

    public List<WithStmt3> commaWithItems() {
        return getList(2, WithStmt3.class);
    }

    public Suite suite() {
        return get(3, Suite.class);
    }

    /**
     * ',' with_item
     */
    public static final class WithStmt3 extends NodeWrapper {

        public WithStmt3(ParseTreeNode node) {
            super(node);
        }

        public WithItem withItem() {
            return get(1, WithItem.class);
        }
    }
}
