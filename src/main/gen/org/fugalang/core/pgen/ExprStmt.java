package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * expr_stmt: ['/'] 'exprlist_star' ('annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist')
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
        addOptional(isTokenDiv(), "/");
        addRequired(exprlistStar());
        addRequired(exprStmt3());
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

    public ExprStmt3 exprStmt3() {
        var element = getItem(2);
        element.failIfAbsent(ExprStmt3.RULE);
        return ExprStmt3.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.consumeToken("/");
        result = ExprlistStar.parse(parseTree, level + 1);
        result = result && ExprStmt3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'annassign' | ('=' 'exprlist_star')+ | 'augassign' 'exprlist'
     */
    public static final class ExprStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:3", RuleType.Disjunction, false);

        public static ExprStmt3 of(ParseTreeNode node) {
            return new ExprStmt3(node);
        }

        private ExprStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<ExprStmt32> exprStmt32List;

        @Override
        protected void buildRule() {
            addChoice(annassignOrNull());
            addChoice(exprStmt32List());
            addChoice(exprStmt33OrNull());
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

        public List<ExprStmt32> exprStmt32List() {
            if (exprStmt32List != null) {
                return exprStmt32List;
            }
            List<ExprStmt32> result = null;
            var element = getItem(1);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(ExprStmt32.of(node));
            }
            exprStmt32List = result == null ? Collections.emptyList() : result;
            return exprStmt32List;
        }

        public ExprStmt33 exprStmt33() {
            var element = getItem(2);
            element.failIfAbsent(ExprStmt33.RULE);
            return ExprStmt33.of(element);
        }

        public ExprStmt33 exprStmt33OrNull() {
            var element = getItem(2);
            if (!element.isPresent(ExprStmt33.RULE)) {
                return null;
            }
            return ExprStmt33.of(element);
        }

        public boolean hasExprStmt33() {
            var element = getItem(2);
            return element.isPresent(ExprStmt33.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Annassign.parse(parseTree, level + 1);
            result = result || parseExprStmt32List(parseTree, level + 1);
            result = result || ExprStmt33.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }

        private static boolean parseExprStmt32List(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            parseTree.enterCollection();
            var result = ExprStmt32.parse(parseTree, level + 1);
            if (result) while (true) {
                var pos = parseTree.position();
                if (!ExprStmt32.parse(parseTree, level + 1) ||
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
    public static final class ExprStmt32 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:3:2", RuleType.Conjunction, false);

        public static ExprStmt32 of(ParseTreeNode node) {
            return new ExprStmt32(node);
        }

        private ExprStmt32(ParseTreeNode node) {
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
    public static final class ExprStmt33 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("expr_stmt:3:3", RuleType.Conjunction, false);

        public static ExprStmt33 of(ParseTreeNode node) {
            return new ExprStmt33(node);
        }

        private ExprStmt33(ParseTreeNode node) {
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
