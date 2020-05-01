package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// or_rule: 'and_rule' ('|' 'and_rule')*
public final class OrRule extends ConjunctionRule {
    public static final String RULE_NAME = "or_rule";

    private final AndRule andRule;
    private final List<OrRule2> orRule2List;

    public OrRule(
            AndRule andRule,
            List<OrRule2> orRule2List
    ) {
        this.andRule = andRule;
        this.orRule2List = orRule2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("andRule", andRule);
        addRequired("orRule2List", orRule2List);
    }

    public AndRule andRule() {
        return andRule;
    }

    public List<OrRule2> orRule2List() {
        return orRule2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = AndRule.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!OrRule2.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    // '|' 'and_rule'
    public static final class OrRule2 extends ConjunctionRule {
        public static final String RULE_NAME = "or_rule:2";

        private final boolean isTokenOr;
        private final AndRule andRule;

        public OrRule2(
                boolean isTokenOr,
                AndRule andRule
        ) {
            this.isTokenOr = isTokenOr;
            this.andRule = andRule;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenOr", isTokenOr);
            addRequired("andRule", andRule);
        }

        public boolean isTokenOr() {
            return isTokenOr;
        }

        public AndRule andRule() {
            return andRule;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("|");
            result = result && AndRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
