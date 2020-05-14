package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

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

    public RepeatRule repeatRule() {
        return RepeatRule.of(getItem(0));
    }

    public List<AndRule2> andRule2List() {
        return getList(1, AndRule2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = RepeatRule.parse(parseTree, level + 1);
        if (result) parseAndRule2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseAndRule2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!AndRule2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
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

        public RepeatRule repeatRule() {
            return RepeatRule.of(getItem(0));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = RepeatRule.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
