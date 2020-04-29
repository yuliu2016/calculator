package org.fugalang.core.pgen;

// with_item: 'expr' ['as' 'NAME']
public class WithItem {
    public final Expr expr;
    public final WithItem2Group withItem2Group;

    public WithItem(
            Expr expr,
            WithItem2Group withItem2Group
    ) {
        this.expr = expr;
        this.withItem2Group = withItem2Group;
    }
}
