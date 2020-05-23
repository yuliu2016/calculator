package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * assignment: ['/'] 'exprlist_star' ['annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist']
 */
public final class Assignment extends NodeWrapper {

    public Assignment(ParseTreeNode node) {
        super(ParserRules.ASSIGNMENT, node);
    }

    public boolean isDiv() {
        return is(0);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar::new);
    }

    public Assignment3 assignment3() {
        return get(2, Assignment3::new);
    }

    public boolean hasAssignment3() {
        return has(2, ParserRules.ASSIGNMENT_3);
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static final class Assignment3 extends NodeWrapper {

        public Assignment3(ParseTreeNode node) {
            super(ParserRules.ASSIGNMENT_3, node);
        }

        public Annassign annassign() {
            return get(0, Annassign::new);
        }

        public boolean hasAnnassign() {
            return has(0, ParserRules.ANNASSIGN);
        }

        public List<Assignment32> exprlistStars() {
            return getList(1, Assignment32::new);
        }

        public Assignment33 augassignExprlist() {
            return get(2, Assignment33::new);
        }

        public boolean hasAugassignExprlist() {
            return has(2, ParserRules.ASSIGNMENT_3_3);
        }
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Assignment32 extends NodeWrapper {

        public Assignment32(ParseTreeNode node) {
            super(ParserRules.ASSIGNMENT_3_2, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar::new);
        }
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static final class Assignment33 extends NodeWrapper {

        public Assignment33(ParseTreeNode node) {
            super(ParserRules.ASSIGNMENT_3_3, node);
        }

        public Augassign augassign() {
            return get(0, Augassign::new);
        }

        public Exprlist exprlist() {
            return get(1, Exprlist::new);
        }
    }
}
