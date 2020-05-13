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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.consumeToken("/");
        result = ExprlistStar.parse(parseTree, level + 1);
        if (result) Assignment3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Annassign.parse(parseTree, level + 1);
            result = result || parseAssignment32List(parseTree, level + 1);
            result = result || Assignment33.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }

        private static boolean parseAssignment32List(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            parseTree.enterCollection();
            var result = Assignment32.parse(parseTree, level + 1);
            if (result) while (true) {
                var pos = parseTree.position();
                if (!Assignment32.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            return result;
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

        public boolean isTokenAssign() {
            return true;
        }

        public ExprlistStar exprlistStar() {
            return ExprlistStar.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("=");
            result = result && ExprlistStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Augassign.parse(parseTree, level + 1);
            result = result && Exprlist.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
