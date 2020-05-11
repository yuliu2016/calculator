package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    protected void buildRule() {
        addOptional(isTokenDiv(), "/");
        addRequired(exprlistStar());
        addOptional(assignment3OrNull());
    }

    public boolean isTokenDiv() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public ExprlistStar exprlistStar() {
        var element = getItem(1);
        element.failIfAbsent(ExprlistStar.RULE);
        return ExprlistStar.of(element);
    }

    public Assignment3 assignment3() {
        var element = getItem(2);
        element.failIfAbsent(Assignment3.RULE);
        return Assignment3.of(element);
    }

    public Assignment3 assignment3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(Assignment3.RULE)) {
            return null;
        }
        return Assignment3.of(element);
    }

    public boolean hasAssignment3() {
        var element = getItem(2);
        return element.isPresent(Assignment3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        private List<Assignment32> assignment32List;

        @Override
        protected void buildRule() {
            addChoice(annassignOrNull());
            addChoice(assignment32List());
            addChoice(assignment33OrNull());
        }

        public Annassign annassign() {
            var element = getItem(0);
            element.failIfAbsent(Annassign.RULE);
            return Annassign.of(element);
        }

        public Annassign annassignOrNull() {
            var element = getItem(0);
            if (!element.isPresent(Annassign.RULE)) {
                return null;
            }
            return Annassign.of(element);
        }

        public boolean hasAnnassign() {
            var element = getItem(0);
            return element.isPresent(Annassign.RULE);
        }

        public List<Assignment32> assignment32List() {
            if (assignment32List != null) {
                return assignment32List;
            }
            List<Assignment32> result = null;
            var element = getItem(1);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(Assignment32.of(node));
            }
            assignment32List = result == null ? Collections.emptyList() : result;
            return assignment32List;
        }

        public Assignment33 assignment33() {
            var element = getItem(2);
            element.failIfAbsent(Assignment33.RULE);
            return Assignment33.of(element);
        }

        public Assignment33 assignment33OrNull() {
            var element = getItem(2);
            if (!element.isPresent(Assignment33.RULE)) {
                return null;
            }
            return Assignment33.of(element);
        }

        public boolean hasAssignment33() {
            var element = getItem(2);
            return element.isPresent(Assignment33.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign(), "=");
            addRequired(exprlistStar());
        }

        public boolean isTokenAssign() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ExprlistStar exprlistStar() {
            var element = getItem(1);
            element.failIfAbsent(ExprlistStar.RULE);
            return ExprlistStar.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addRequired(augassign());
            addRequired(exprlist());
        }

        public Augassign augassign() {
            var element = getItem(0);
            element.failIfAbsent(Augassign.RULE);
            return Augassign.of(element);
        }

        public Exprlist exprlist() {
            var element = getItem(1);
            element.failIfAbsent(Exprlist.RULE);
            return Exprlist.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Augassign.parse(parseTree, level + 1);
            result = result && Exprlist.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
