package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_stmt", RuleType.Conjunction);

    public static SimpleStmt of(ParseTreeNode node) {
        return new SimpleStmt(node);
    }

    private SimpleStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public SmallStmt smallStmt() {
        return get(0, SmallStmt::of);
    }

    public List<SimpleStmt2> smallStmts() {
        return getList(1, SimpleStmt2::of);
    }

    public boolean isSemicolon() {
        return is(2);
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("simple_stmt:2", RuleType.Conjunction);

        public static SimpleStmt2 of(ParseTreeNode node) {
            return new SimpleStmt2(node);
        }

        private SimpleStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SmallStmt smallStmt() {
            return get(1, SmallStmt::of);
        }
    }
}
