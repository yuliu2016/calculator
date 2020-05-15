package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * assignment: ['/'] 'exprlist_star' ['annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist']
 */
public final class Assignment extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("assignment", RuleType.Conjunction, true);

    public static Assignment of(ParseTreeNode node) {
        return new Assignment(node);
    }

    private Assignment(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTokenDiv() {
        return getBoolean(0);
    }

    public ExprlistStar exprlistStar() {
        return ExprlistStar.of(getItem(1));
    }

    public Assignment3 assignment3() {
        return Assignment3.of(getItem(2));
    }

    public boolean hasAssignment3() {
        return hasItemOfRule(2, Assignment3.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        t.consumeToken("/");
        r = ExprlistStar.parse(t, l + 1);
        if (r) Assignment3.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static final class Assignment3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("assignment:3", RuleType.Disjunction, false);

        public static Assignment3 of(ParseTreeNode node) {
            return new Assignment3(node);
        }

        private Assignment3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Annassign annassign() {
            return Annassign.of(getItem(0));
        }

        public boolean hasAnnassign() {
            return hasItemOfRule(0, Annassign.RULE);
        }

        public List<Assignment32> assignment32List() {
            return getList(1, Assignment32::of);
        }

        public Assignment33 assignment33() {
            return Assignment33.of(getItem(2));
        }

        public boolean hasAssignment33() {
            return hasItemOfRule(2, Assignment33.RULE);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = Annassign.parse(t, l + 1);
            r = r || parseAssignment32List(t, l);
            r = r || Assignment33.parse(t, l + 1);
            t.exit(r);
            return r;
        }

        private static boolean parseAssignment32List(ParseTree t, int l) {
            t.enterCollection();
            var r = Assignment32.parse(t, l + 1);
            if (r) while (true) {
                var p = t.position();
                if (!Assignment32.parse(t, l + 1)) break;
                if (t.guardLoopExit(p)) break;
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
                new ParserRule("assignment:3:2", RuleType.Conjunction, false);

        public static Assignment32 of(ParseTreeNode node) {
            return new Assignment32(node);
        }

        private Assignment32(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprlistStar exprlistStar() {
            return ExprlistStar.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("=");
            r = r && ExprlistStar.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static final class Assignment33 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("assignment:3:3", RuleType.Conjunction, false);

        public static Assignment33 of(ParseTreeNode node) {
            return new Assignment33(node);
        }

        private Assignment33(ParseTreeNode node) {
            super(RULE, node);
        }

        public Augassign augassign() {
            return Augassign.of(getItem(0));
        }

        public Exprlist exprlist() {
            return Exprlist.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = Augassign.parse(t, l + 1);
            r = r && Exprlist.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
