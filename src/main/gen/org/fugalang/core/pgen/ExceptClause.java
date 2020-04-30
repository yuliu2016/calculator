package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public final class ExceptClause extends ConjunctionRule {
    private final boolean isTokenExcept;
    private final ExceptClause2Group exceptClause2Group;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2Group exceptClause2Group
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2Group = exceptClause2Group;

        addRequired("isTokenExcept", isTokenExcept);
        addOptional("exceptClause2Group", exceptClause2Group);
    }

    public boolean isTokenExcept() {
        return isTokenExcept;
    }

    public Optional<ExceptClause2Group> exceptClause2Group() {
        return Optional.ofNullable(exceptClause2Group);
    }

    // 'expr' ['as' 'NAME']
    public static final class ExceptClause2Group extends ConjunctionRule {
        private final Expr expr;
        private final ExceptClause2Group2Group exceptClause2Group2Group;

        public ExceptClause2Group(
                Expr expr,
                ExceptClause2Group2Group exceptClause2Group2Group
        ) {
            this.expr = expr;
            this.exceptClause2Group2Group = exceptClause2Group2Group;

            addRequired("expr", expr);
            addOptional("exceptClause2Group2Group", exceptClause2Group2Group);
        }

        public Expr expr() {
            return expr;
        }

        public Optional<ExceptClause2Group2Group> exceptClause2Group2Group() {
            return Optional.ofNullable(exceptClause2Group2Group);
        }
    }

    // 'as' 'NAME'
    public static final class ExceptClause2Group2Group extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public ExceptClause2Group2Group(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;

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
