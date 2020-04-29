package org.fugalang.core.pgen;

// with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
public class WithStmt {
    public final boolean isTokenWith;
    public final WithItem withItem;
    public final WithStmt3Group withStmt3Group;
    public final Suite suite;

    public WithStmt(
            boolean isTokenWith,
            WithItem withItem,
            WithStmt3Group withStmt3Group,
            Suite suite
    ) {
        this.isTokenWith = isTokenWith;
        this.withItem = withItem;
        this.withStmt3Group = withStmt3Group;
        this.suite = suite;
    }
}
