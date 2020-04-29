package org.fugalang.core.pgen;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public class ExceptClause {
    private final boolean isTokenExcept;
    private final ExceptClause2Group exceptClause2Group;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2Group exceptClause2Group
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2Group = exceptClause2Group;
    }

    public boolean getIsTokenExcept() {
        return isTokenExcept;
    }

    public ExceptClause2Group getExceptClause2Group() {
        return exceptClause2Group;
    }

    // 'expr' ['as' 'NAME']
    public static class ExceptClause2Group {
        private final Expr expr;
        private final ExceptClause2Group2Group exceptClause2Group2Group;

        public ExceptClause2Group(
                Expr expr,
                ExceptClause2Group2Group exceptClause2Group2Group
        ) {
            this.expr = expr;
            this.exceptClause2Group2Group = exceptClause2Group2Group;
        }

        public Expr getExpr() {
            return expr;
        }

        public ExceptClause2Group2Group getExceptClause2Group2Group() {
            return exceptClause2Group2Group;
        }
    }

    // 'as' 'NAME'
    public static class ExceptClause2Group2Group {
        private final boolean isTokenAs;
        private final Object name;

        public ExceptClause2Group2Group(
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
