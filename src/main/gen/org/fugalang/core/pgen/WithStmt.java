package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
public final class WithStmt extends ConjunctionRule {
    private final boolean isTokenWith;
    private final WithItem withItem;
    private final List<WithStmt3Group> withStmt3GroupList;
    private final Suite suite;

    public WithStmt(
            boolean isTokenWith,
            WithItem withItem,
            List<WithStmt3Group> withStmt3GroupList,
            Suite suite
    ) {
        this.isTokenWith = isTokenWith;
        this.withItem = withItem;
        this.withStmt3GroupList = withStmt3GroupList;
        this.suite = suite;

        addRequired("isTokenWith", isTokenWith);
        addRequired("withItem", withItem);
        addRequired("withStmt3GroupList", withStmt3GroupList);
        addRequired("suite", suite);
    }

    public boolean isTokenWith() {
        return isTokenWith;
    }

    public WithItem withItem() {
        return withItem;
    }

    public List<WithStmt3Group> withStmt3GroupList() {
        return withStmt3GroupList;
    }

    public Suite suite() {
        return suite;
    }

    // ',' 'with_item'
    public static final class WithStmt3Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final WithItem withItem;

        public WithStmt3Group(
                boolean isTokenComma,
                WithItem withItem
        ) {
            this.isTokenComma = isTokenComma;
            this.withItem = withItem;

            addRequired("isTokenComma", isTokenComma);
            addRequired("withItem", withItem);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public WithItem withItem() {
            return withItem;
        }
    }
}
