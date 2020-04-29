package org.fugalang.core.pgen;

import java.util.List;

// with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
public class WithStmt {
    public final boolean isTokenWith;
    public final WithItem withItem;
    public final List<WithStmt3Group> withStmt3GroupList;
    public final Suite suite;

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
}
