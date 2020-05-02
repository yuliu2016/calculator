package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("disjunction", RuleType.Conjunction, true);

    public static Disjunction of(ParseTreeNode node) {
        return new Disjunction(node);
    }

    private Disjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Disjunction2> disjunction2List;

    @Override
    protected void buildRule() {
        addRequired("conjunction", conjunction());
        addRequired("disjunction2List", disjunction2List());
    }

    public Conjunction conjunction() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Conjunction.of(element);
    }

    public List<Disjunction2> disjunction2List() {
        return disjunction2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Conjunction.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Disjunction2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'or' 'conjunction'
     */
    public static final class Disjunction2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("disjunction:2", RuleType.Conjunction, false);

        public static Disjunction2 of(ParseTreeNode node) {
            return new Disjunction2(node);
        }

        private Disjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenOr", isTokenOr());
            addRequired("conjunction", conjunction());
        }

        public boolean isTokenOr() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Conjunction conjunction() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Conjunction.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("or");
            result = result && Conjunction.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
