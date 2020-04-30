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

        addRequired("exprlistStar", exprlistStar);
        addOptional("exprStmt2Group", exprStmt2Group);
    }

    public ExprlistStar exprlistStar() {
        return exprlistStar;
    }

    public Optional<ExprStmt2Group> exprStmt2Group() {
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

            addChoice("exprStmt2Group1", exprStmt2Group1);
            addChoice("exprStmt2Group2GroupList", exprStmt2Group2GroupList);
        }

        public ExprStmt2Group1 exprStmt2Group1() {
            return exprStmt2Group1;
        }

        public List<ExprStmt2Group2Group> exprStmt2Group2GroupList() {
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
    public static final class ExprStmt2Group2Group extends ConjunctionRule {
        private final boolean isTokenAssign;
        private final ExprlistStar exprlistStar;

        public ExprStmt2Group2Group(
                boolean isTokenAssign,
                ExprlistStar exprlistStar
        ) {
            this.isTokenAssign = isTokenAssign;
            this.exprlistStar = exprlistStar;

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
