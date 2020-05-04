package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * expr_stmt: 'exprlist_star' ['augassign' 'exprlist' | ('=' 'exprlist_star')*]
 */
public final class ExprStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("expr_stmt", RuleType.Conjunction, true);

    public static ExprStmt of(ParseTreeNode node) {
        return new ExprStmt(node);
    }

    private ExprStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(exprlistStar());
        addOptional(exprStmt2());
    }

    public ExprlistStar exprlistStar() {
        var element = getItem(0);
        element.failIfAbsent(ExprlistStar.RULE);
        return ExprlistStar.of(element);
    }

    public ExprStmt2 exprStmt2() {
        var element = getItem(1);
        if (!element.isPresent(ExprStmt2.RULE)) {
            return null;
        }
        return ExprStmt2.of(element);
    }

    public boolean hasExprStmt2() {
        return exprStmt2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ExprlistStar.parse(parseTree, level + 1);
        ExprStmt2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'augassign' 'exprlist' | ('=' 'exprlist_star')*
     */
    public static final class ExprStmt2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:2", RuleType.Disjunction, false);

        public static ExprStmt2 of(ParseTreeNode node) {
            return new ExprStmt2(node);
        }

        private ExprStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<ExprStmt22> exprStmt22List;

        @Override
        protected void buildRule() {
            addChoice(exprStmt21());
            addChoice(exprStmt22List());
        }

        public ExprStmt21 exprStmt21() {
            var element = getItem(0);
            if (!element.isPresent(ExprStmt21.RULE)) {
                return null;
            }
            return ExprStmt21.of(element);
        }

        public boolean hasExprStmt21() {
            return exprStmt21() != null;
        }

        public List<ExprStmt22> exprStmt22List() {
            if (exprStmt22List != null) {
                return exprStmt22List;
            }
            List<ExprStmt22> result = null;
            var element = getItem(1);
            for (var node : element.asCollection()) {
                if (result == null) result = new ArrayList<>();
                result.add(ExprStmt22.of(node));
            }
            exprStmt22List = result == null ? Collections.emptyList() : result;
            return exprStmt22List;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = ExprStmt21.parse(parseTree, level + 1);
            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!ExprStmt22.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'augassign' 'exprlist'
     */
    public static final class ExprStmt21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:2:1", RuleType.Conjunction, false);

        public static ExprStmt21 of(ParseTreeNode node) {
            return new ExprStmt21(node);
        }

        private ExprStmt21(ParseTreeNode node) {
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

    /**
     * '=' 'exprlist_star'
     */
    public static final class ExprStmt22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:2:2", RuleType.Conjunction, false);

        public static ExprStmt22 of(ParseTreeNode node) {
            return new ExprStmt22(node);
        }

        private ExprStmt22(ParseTreeNode node) {
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
}
