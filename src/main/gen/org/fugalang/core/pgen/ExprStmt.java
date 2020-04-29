package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// expr_stmt: 'exprlist_star' ['augassign' 'exprlist' | ('=' 'exprlist_star')*]
public final class ExprStmt extends ConjunctionRule {
    private final ExprlistStar exprlistStar;
    private final ExprStmt2Group exprStmt2Group;

    public ExprStmt(
            ExprlistStar exprlistStar,
            ExprStmt2Group exprStmt2Group
    ) {
        this.exprlistStar = exprlistStar;
        this.exprStmt2Group = exprStmt2Group;
    }

    public ExprlistStar getExprlistStar() {
        return exprlistStar;
    }

    public Optional<ExprStmt2Group> getExprStmt2Group() {
        return Optional.ofNullable(exprStmt2Group);
    }

    // 'augassign' 'exprlist' | ('=' 'exprlist_star')*
    public static final class ExprStmt2Group extends DisjunctionRule {
        private final ExprStmt2Group1 exprStmt2Group1;
        private final List<ExprStmt2Group2Group> exprStmt2Group2GroupList;

        public ExprStmt2Group(
                ExprStmt2Group1 exprStmt2Group1,
                List<ExprStmt2Group2Group> exprStmt2Group2GroupList
        ) {
            this.exprStmt2Group1 = exprStmt2Group1;
            this.exprStmt2Group2GroupList = exprStmt2Group2GroupList;
        }

        public ExprStmt2Group1 getExprStmt2Group1() {
            return exprStmt2Group1;
        }

        public List<ExprStmt2Group2Group> getExprStmt2Group2GroupList() {
            return exprStmt2Group2GroupList;
        }
    }

    // 'augassign' 'exprlist'
    public static final class ExprStmt2Group1 extends ConjunctionRule {
        private final Augassign augassign;
        private final Exprlist exprlist;

        public ExprStmt2Group1(
                Augassign augassign,
                Exprlist exprlist
        ) {
            this.augassign = augassign;
            this.exprlist = exprlist;
        }

        public Augassign getAugassign() {
            return augassign;
        }

        public Exprlist getExprlist() {
            return exprlist;
        }
    }

    // '=' 'exprlist_star'
    public static final class ExprStmt2Group2Group extends ConjunctionRule {
        private final boolean isTokenAssign;
        private final ExprlistStar exprlistStar;

        public ExprStmt2Group2Group(
                boolean isTokenAssign,
                ExprlistStar exprlistStar
        ) {
            this.isTokenAssign = isTokenAssign;
            this.exprlistStar = exprlistStar;
        }

        public boolean getIsTokenAssign() {
            return isTokenAssign;
        }

        public ExprlistStar getExprlistStar() {
            return exprlistStar;
        }
    }
}
