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

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        t.consume("/");
        r = ExprlistStar.parse(t, lv + 1);
        if (r) Assignment3.parse(t, lv + 1);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Annassign.parse(t, lv + 1);
            r = r || parseExprlistStars(t, lv);
            r = r || Assignment33.parse(t, lv + 1);
            t.exit(r);
            return r;
        }

        private static boolean parseExprlistStars(ParseTree t, int lv) {
            t.enterCollection();
            var r = Assignment32.parse(t, lv + 1);
            if (r) while (true) {
                var p = t.position();
                if (!Assignment32.parse(t, lv + 1) || t.loopGuard(p)) break;
            }
            t.exitCollection();
            return r;
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

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("=");
            r = r && ExprlistStar.parse(t, lv + 1);
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Augassign.parse(t, lv + 1);
            r = r && Exprlist.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
