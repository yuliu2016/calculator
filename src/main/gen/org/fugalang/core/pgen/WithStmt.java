package org.fugalang.core.pgen;

import java.util.List;

// with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
public class WithStmt {
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
    }

    public boolean getIsTokenWith() {
        return isTokenWith;
    }

    public WithItem getWithItem() {
        return withItem;
    }

    public List<WithStmt3Group> getWithStmt3GroupList() {
        return withStmt3GroupList;
    }

    public Suite getSuite() {
        return suite;
    }

    // ',' 'with_item'
    public static class WithStmt3Group {
        private final boolean isTokenComma;
        private final WithItem withItem;

        public WithStmt3Group(
                boolean isTokenComma,
                WithItem withItem
        ) {
            this.isTokenComma = isTokenComma;
            this.withItem = withItem;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public WithItem getWithItem() {
            return withItem;
        }
    }
}
