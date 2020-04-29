package org.fugalang.core.pgen;

// ',' 'with_item'
public class WithStmt3Group {
    public final boolean isTokenComma;
    public final WithItem withItem;

    public WithStmt3Group(
            boolean isTokenComma,
            WithItem withItem
    ) {
        this.isTokenComma = isTokenComma;
        this.withItem = withItem;
    }
}
