package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * and_rule: 'repeat_rule' ('repeat_rule')*
 */
public final class AndRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("and_rule", RuleType.Conjunction, true);

    public static AndRule of(ParseTreeNode node) {
        return new AndRule(node);
    }

    private AndRule(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<AndRule2> andRule2List;

    @Override
    protected void buildRule() {
        addRequired(repeatRule());
        addRequired(andRule2List());
    }

    public RepeatRule repeatRule() {
        var element = getItem(0);
        element.failIfAbsent(RepeatRule.RULE);
        return RepeatRule.of(element);
    }

    public List<AndRule2> andRule2List() {
        if (andRule2List != null) {
            return andRule2List;
        }
        List<AndRule2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(AndRule2.of(node));
        }
        andRule2List = result == null ? Collections.emptyList() : result;
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
        if (result) while (true) {
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
    public static final class AndRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("and_rule:2", RuleType.Conjunction, false);

        public static AndRule2 of(ParseTreeNode node) {
            return new AndRule2(node);
        }

        private AndRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(repeatRule());
        }

        public RepeatRule repeatRule() {
            var element = getItem(0);
            element.failIfAbsent(RepeatRule.RULE);
            return RepeatRule.of(element);
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
