package org.fugalang.core.pgen;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public class ExceptClause {
    public final boolean isTokenExcept;
    public final ExceptClause2Group exceptClause2Group;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2Group exceptClause2Group
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2Group = exceptClause2Group;
    }

    // 'expr' ['as' 'NAME']
    public static class ExceptClause2Group {
        public final Expr expr;
        public final ExceptClause2Group2Group exceptClause2Group2Group;

        public ExceptClause2Group(
                Expr expr,
                ExceptClause2Group2Group exceptClause2Group2Group
        ) {
            this.expr = expr;
            this.exceptClause2Group2Group = exceptClause2Group2Group;
        }
    }

    // 'as' 'NAME'
    public static class ExceptClause2Group2Group {
        public final boolean isTokenAs;
        public final Object name;

        public ExceptClause2Group2Group(
                boolean isTokenAs,
                Object name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }
    }
}
