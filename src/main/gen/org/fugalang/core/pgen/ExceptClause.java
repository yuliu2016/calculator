package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public final class ExceptClause extends ConjunctionRule {
    private final boolean isTokenExcept;
    private final ExceptClause2 exceptClause2;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2 exceptClause2
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2 = exceptClause2;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenExcept", isTokenExcept);
        addOptional("exceptClause2", exceptClause2);
    }

    public boolean isTokenExcept() {
        return isTokenExcept;
    }

    public Optional<ExceptClause2> exceptClause2() {
        return Optional.ofNullable(exceptClause2);
    }

    // 'expr' ['as' 'NAME']
    public static final class ExceptClause2 extends ConjunctionRule {
        private final Expr expr;
        private final ExceptClause22 exceptClause22;

        public ExceptClause2(
                Expr expr,
                ExceptClause22 exceptClause22
        ) {
            this.expr = expr;
            this.exceptClause22 = exceptClause22;
        }

        @Override
        protected void buildRule() {
            addRequired("expr", expr);
            addOptional("exceptClause22", exceptClause22);
        }

        public Expr expr() {
            return expr;
        }

        public Optional<ExceptClause22> exceptClause22() {
            return Optional.ofNullable(exceptClause22);
        }
    }

    // 'as' 'NAME'
    public static final class ExceptClause22 extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public ExceptClause22(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs);
            addRequired("name", name);
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }
    }
}
