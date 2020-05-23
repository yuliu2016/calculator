package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * assignment: ['/'] 'exprlist_star' ['annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist']
 */
public final class Assignment extends NodeWrapper {

    public Assignment(ParseTreeNode node) {
        super(FugaRules.ASSIGNMENT, node);
    }

    public boolean isDiv() {
        return is(0);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar.class);
    }

    public Assignment3 assignment3() {
        return get(2, Assignment3.class);
    }

    public boolean hasAssignment3() {
        return has(2);
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static final class Assignment3 extends NodeWrapper {

        public Assignment3(ParseTreeNode node) {
            super(FugaRules.ASSIGNMENT_3, node);
        }

        public Annassign annassign() {
            return get(0, Annassign.class);
        }

        public boolean hasAnnassign() {
            return has(0);
        }

        public List<Assignment32> exprlistStars() {
            return getList(1, Assignment32.class);
        }

        public Assignment33 augassignExprlist() {
            return get(2, Assignment33.class);
        }

        public boolean hasAugassignExprlist() {
            return has(2);
        }
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Assignment32 extends NodeWrapper {

        public Assignment32(ParseTreeNode node) {
            super(FugaRules.ASSIGNMENT_3_2, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar.class);
        }
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static final class Assignment33 extends NodeWrapper {

        public Assignment33(ParseTreeNode node) {
            super(FugaRules.ASSIGNMENT_3_3, node);
        }

        public Augassign augassign() {
            return get(0, Augassign.class);
        }

        public Exprlist exprlist() {
            return get(1, Exprlist.class);
        }
    }
}
