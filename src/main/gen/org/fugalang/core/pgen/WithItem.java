package org.fugalang.core.pgen;

// with_item: 'expr' ['as' 'NAME']
public class WithItem {
    private final Expr expr;
    private final WithItem2Group withItem2Group;

    public WithItem(
            Expr expr,
            WithItem2Group withItem2Group
    ) {
        this.expr = expr;
        this.withItem2Group = withItem2Group;
    }

    public Expr getExpr() {
        return expr;
    }

    public WithItem2Group getWithItem2Group() {
        return withItem2Group;
    }

    // 'as' 'NAME'
    public static class WithItem2Group {
        private final boolean isTokenAs;
        private final Object name;

        public WithItem2Group(
                boolean isTokenAs,
                Object name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        public boolean getIsTokenAs() {
            return isTokenAs;
        }

        public Object getName() {
            return name;
        }
    }
}
