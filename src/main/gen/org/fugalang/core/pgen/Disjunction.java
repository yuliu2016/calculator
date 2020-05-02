package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("disjunction", RuleType.Conjunction, true);

    private final Conjunction conjunction;
    private final List<Disjunction2> disjunction2List;

    public Disjunction(
            Conjunction conjunction,
            List<Disjunction2> disjunction2List
    ) {
        this.conjunction = conjunction;
        this.disjunction2List = disjunction2List;
    }

    @Override
    protected void buildRule() {
        addRequired("conjunction", conjunction());
        addRequired("disjunction2List", disjunction2List());
    }

    public Conjunction conjunction() {
        return conjunction;
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
    public static final class Disjunction2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("disjunction:2", RuleType.Conjunction, false);

        private final boolean isTokenOr;
        private final Conjunction conjunction;

        public Disjunction2(
                boolean isTokenOr,
                Conjunction conjunction
        ) {
            this.isTokenOr = isTokenOr;
            this.conjunction = conjunction;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenOr", isTokenOr());
            addRequired("conjunction", conjunction());
        }

        public boolean isTokenOr() {
            return isTokenOr;
        }

        public Conjunction conjunction() {
            return conjunction;
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
