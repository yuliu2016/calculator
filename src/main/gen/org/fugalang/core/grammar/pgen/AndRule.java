package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * and_rule: 'repeat_rule' ('repeat_rule')*
 */
public final class AndRule extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("and_rule", RuleType.Conjunction, true);

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
        addRequired("repeatRule", repeatRule());
        addRequired("andRule2List", andRule2List());
    }

    public RepeatRule repeatRule() {
        return repeatRule;
    }

    public List<AndRule2> andRule2List() {
        return andRule2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = RepeatRule.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!AndRule2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'repeat_rule'
     */
    public static final class AndRule2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("and_rule:2", RuleType.Conjunction, false);

        private final RepeatRule repeatRule;

        public AndRule2(
                RepeatRule repeatRule
        ) {
            this.repeatRule = repeatRule;
        }

        @Override
        protected void buildRule() {
            addRequired("repeatRule", repeatRule());
        }

        public RepeatRule repeatRule() {
            return repeatRule;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = RepeatRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
