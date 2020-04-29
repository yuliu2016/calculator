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

    // 'as' 'NAME'
    public static class WithItem2Group {
        public final boolean isTokenAs;
        public final Object name;

        public WithItem2Group(
                boolean isTokenAs,
                Object name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }
    }
}
