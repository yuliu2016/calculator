package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * assignment: ['/'] 'exprlist_star' ['annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist']
 */
public final class Assignment extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("assignment", RuleType.Conjunction);

    public static Assignment of(ParseTreeNode node) {
        return new Assignment(node);
    }

    private Assignment(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isDiv() {
        return is(0);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar::of);
    }

    public Assignment3 assignment3() {
        return get(2, Assignment3::of);
    }

    public boolean hasAssignment3() {
        return has(2, Assignment3.RULE);
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static final class Assignment3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("assignment:3", RuleType.Disjunction);

        public static Assignment3 of(ParseTreeNode node) {
            return new Assignment3(node);
        }

        private Assignment3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Annassign annassign() {
            return get(0, Annassign::of);
        }

        public boolean hasAnnassign() {
            return has(0, Annassign.RULE);
        }

        public List<Assignment32> exprlistStars() {
            return getList(1, Assignment32::of);
        }

        public Assignment33 augassignExprlist() {
            return get(2, Assignment33::of);
        }

        public boolean hasAugassignExprlist() {
            return has(2, Assignment33.RULE);
        }
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Assignment32 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("assignment:3:2", RuleType.Conjunction);

        public static Assignment32 of(ParseTreeNode node) {
            return new Assignment32(node);
        }

        private Assignment32(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprlistStar exprlistStar() {
            return get(1, ExprlistStar::of);
        }
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static final class Assignment33 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("assignment:3:3", RuleType.Conjunction);

        public static Assignment33 of(ParseTreeNode node) {
            return new Assignment33(node);
        }

        private Assignment33(ParseTreeNode node) {
            super(RULE, node);
        }

        public Augassign augassign() {
            return get(0, Augassign::of);
        }

        public Exprlist exprlist() {
            return get(1, Exprlist::of);
        }
    }
}
