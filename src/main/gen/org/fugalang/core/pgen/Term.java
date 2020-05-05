package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * term: 'pipe_expr' (('*' | '@' | '/' | '%' | '//') 'pipe_expr')*
 */
public final class Term extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("term", RuleType.Conjunction, true);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Term2> term2List;

    @Override
    protected void buildRule() {
        addRequired(pipeExpr());
        addRequired(term2List());
    }

    public PipeExpr pipeExpr() {
        var element = getItem(0);
        element.failIfAbsent(PipeExpr.RULE);
        return PipeExpr.of(element);
    }

    public List<Term2> term2List() {
        if (term2List != null) {
            return term2List;
        }
        List<Term2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Term2.of(node));
        }
        term2List = result == null ? Collections.emptyList() : result;
        return term2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = PipeExpr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Term2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('*' | '@' | '/' | '%' | '//') 'pipe_expr'
     */
    public static final class Term2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2", RuleType.Conjunction, false);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(term21());
            addRequired(pipeExpr());
        }

        public Term21 term21() {
            var element = getItem(0);
            element.failIfAbsent(Term21.RULE);
            return Term21.of(element);
        }

        public PipeExpr pipeExpr() {
            var element = getItem(1);
            element.failIfAbsent(PipeExpr.RULE);
            return PipeExpr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Term21.parse(parseTree, level + 1);
            result = result && PipeExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '*' | '@' | '/' | '%' | '//'
     */
    public static final class Term21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2:1", RuleType.Disjunction, false);

        public static Term21 of(ParseTreeNode node) {
            return new Term21(node);
        }

        private Term21(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice(isTokenTimes(), "*");
            addChoice(isTokenMatrixTimes(), "@");
            addChoice(isTokenDiv(), "/");
            addChoice(isTokenModulus(), "%");
            addChoice(isTokenFloorDiv(), "//");
        }

        public boolean isTokenTimes() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenMatrixTimes() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public boolean isTokenDiv() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public boolean isTokenModulus() {
            var element = getItem(3);
            return element.asBoolean();
        }

        public boolean isTokenFloorDiv() {
            var element = getItem(4);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("*");
            result = result || parseTree.consumeToken("@");
            result = result || parseTree.consumeToken("/");
            result = result || parseTree.consumeToken("%");
            result = result || parseTree.consumeToken("//");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
