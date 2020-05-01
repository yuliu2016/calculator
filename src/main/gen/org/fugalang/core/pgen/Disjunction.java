package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// disjunction: 'conjunction' ('or' 'conjunction')*
public final class Disjunction extends ConjunctionRule {
    public static final String RULE_NAME = "disjunction";

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
        setExplicitName(RULE_NAME);
        addRequired("conjunction", conjunction);
        addRequired("disjunction2List", disjunction2List);
    }

    public Conjunction conjunction() {
        return conjunction;
    }

    public List<Disjunction2> disjunction2List() {
        return disjunction2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Conjunction.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!Disjunction2.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    // 'or' 'conjunction'
    public static final class Disjunction2 extends ConjunctionRule {
        public static final String RULE_NAME = "disjunction:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenOr", isTokenOr);
            addRequired("conjunction", conjunction);
        }

        public boolean isTokenOr() {
            return isTokenOr;
        }

        public Conjunction conjunction() {
            return conjunction;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("or");
            result = result && Conjunction.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
