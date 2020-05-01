package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// and_rule: 'repeat_rule' ('repeat_rule')*
public final class AndRule extends ConjunctionRule {
    public static final String RULE_NAME = "and_rule";

    private final RepeatRule repeatRule;
    private final List<AndRule2> andRule2List;

    public AndRule(
            RepeatRule repeatRule,
            List<AndRule2> andRule2List
    ) {
        this.repeatRule = repeatRule;
        this.andRule2List = andRule2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("repeatRule", repeatRule);
        addRequired("andRule2List", andRule2List);
    }

    public RepeatRule repeatRule() {
        return repeatRule;
    }

    public List<AndRule2> andRule2List() {
        return andRule2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = RepeatRule.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!AndRule2.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    // 'repeat_rule'
    public static final class AndRule2 extends ConjunctionRule {
        public static final String RULE_NAME = "and_rule:2";

        private final RepeatRule repeatRule;

        public AndRule2(
                RepeatRule repeatRule
        ) {
            this.repeatRule = repeatRule;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("repeatRule", repeatRule);
        }

        public RepeatRule repeatRule() {
            return repeatRule;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = RepeatRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
