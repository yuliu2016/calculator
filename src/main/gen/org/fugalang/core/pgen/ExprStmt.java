package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// expr_stmt: 'exprlist_star' ['augassign' 'exprlist' | ('=' 'exprlist_star')*]
public final class ExprStmt extends ConjunctionRule {
    public static final String RULE_NAME = "expr_stmt";

    private final ExprlistStar exprlistStar;
    private final ExprStmt2 exprStmt2;

    public ExprStmt(
            ExprlistStar exprlistStar,
            ExprStmt2 exprStmt2
    ) {
        this.exprlistStar = exprlistStar;
        this.exprStmt2 = exprStmt2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("exprlistStar", exprlistStar);
        addOptional("exprStmt2", exprStmt2);
    }

    public ExprlistStar exprlistStar() {
        return exprlistStar;
    }

    public Optional<ExprStmt2> exprStmt2() {
        return Optional.ofNullable(exprStmt2);
    }

    // 'augassign' 'exprlist' | ('=' 'exprlist_star')*
    public static final class ExprStmt2 extends DisjunctionRule {
        public static final String RULE_NAME = "expr_stmt:2";

        private final ExprStmt21 exprStmt21;
        private final List<ExprStmt22> exprStmt22List;

        public ExprStmt2(
                ExprStmt21 exprStmt21,
                List<ExprStmt22> exprStmt22List
        ) {
            this.exprStmt21 = exprStmt21;
            this.exprStmt22List = exprStmt22List;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("exprStmt21", exprStmt21);
            addChoice("exprStmt22List", exprStmt22List);
        }

        public ExprStmt21 exprStmt21() {
            return exprStmt21;
        }

        public List<ExprStmt22> exprStmt22List() {
            return exprStmt22List;
        }
    }

    // 'augassign' 'exprlist'
    public static final class ExprStmt21 extends ConjunctionRule {
        public static final String RULE_NAME = "expr_stmt:2:1";

        private final Augassign augassign;
        private final Exprlist exprlist;

        public ExprStmt21(
                Augassign augassign,
                Exprlist exprlist
        ) {
            this.augassign = augassign;
            this.exprlist = exprlist;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("augassign", augassign);
            addRequired("exprlist", exprlist);
        }

        public Augassign augassign() {
            return augassign;
        }

        public Exprlist exprlist() {
            return exprlist;
        }
    }

    // '=' 'exprlist_star'
    public static final class ExprStmt22 extends ConjunctionRule {
        public static final String RULE_NAME = "expr_stmt:2:2";

        private final boolean isTokenAssign;
        private final ExprlistStar exprlistStar;

        public ExprStmt22(
                boolean isTokenAssign,
                ExprlistStar exprlistStar
        ) {
            this.isTokenAssign = isTokenAssign;
            this.exprlistStar = exprlistStar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenAssign", isTokenAssign);
            addRequired("exprlistStar", exprlistStar);
        }

        public boolean isTokenAssign() {
            return isTokenAssign;
        }

        public ExprlistStar exprlistStar() {
            return exprlistStar;
        }
    }
}
