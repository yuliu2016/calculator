package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("with_stmt", RuleType.Conjunction);

    public static WithStmt of(ParseTreeNode node) {
        return new WithStmt(node);
    }

    private WithStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public WithItem withItem() {
        return get(1, WithItem::of);
    }

    public List<WithStmt3> withItems() {
        return getList(2, WithStmt3::of);
    }

    public Suite suite() {
        return get(3, Suite::of);
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("with_stmt:3", RuleType.Conjunction);

        public static WithStmt3 of(ParseTreeNode node) {
            return new WithStmt3(node);
        }

        private WithStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public WithItem withItem() {
            return get(1, WithItem::of);
        }
    }
}
