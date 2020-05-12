package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.token.MetaTokenType;
import org.fugalang.core.parser.*;

/**
 * repeat_rule: 'sub_rule' ['*' | '+']
 */
public final class RepeatRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("repeat_rule", RuleType.Conjunction, true);

    public static RepeatRule of(ParseTreeNode node) {
        return new RepeatRule(node);
    }

    private RepeatRule(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(subRule());
        addOptional(repeatRule2OrNull());
    }

    public SubRule subRule() {
        var element = getItem(0);
        element.failIfAbsent(SubRule.RULE);
        return SubRule.of(element);
    }

    public RepeatRule2 repeatRule2() {
        var element = getItem(1);
        element.failIfAbsent(RepeatRule2.RULE);
        return RepeatRule2.of(element);
    }

    public RepeatRule2 repeatRule2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(RepeatRule2.RULE)) {
            return null;
        }
        return RepeatRule2.of(element);
    }

    public boolean hasRepeatRule2() {
        var element = getItem(1);
        return element.isPresent(RepeatRule2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SubRule.parse(parseTree, level + 1);
        if (result) RepeatRule2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '*' | '+'
     */
    public static final class RepeatRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("repeat_rule:2", RuleType.Disjunction, false);

        public static RepeatRule2 of(ParseTreeNode node) {
            return new RepeatRule2(node);
        }

        private RepeatRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice(starOrNull());
            addChoice(plusOrNull());
        }

        public String star() {
            var element = getItem(0);
            element.failIfAbsent(MetaTokenType.STAR);
            return element.asString();
        }

        public String starOrNull() {
            var element = getItem(0);
            if (!element.isPresent(MetaTokenType.STAR)) {
                return null;
            }
            return element.asString();
        }

        public boolean hasStar() {
            var element = getItem(0);
            return element.isPresent(MetaTokenType.STAR);
        }

        public String plus() {
            var element = getItem(1);
            element.failIfAbsent(MetaTokenType.PLUS);
            return element.asString();
        }

        public String plusOrNull() {
            var element = getItem(1);
            if (!element.isPresent(MetaTokenType.PLUS)) {
                return null;
            }
            return element.asString();
        }

        public boolean hasPlus() {
            var element = getItem(1);
            return element.isPresent(MetaTokenType.PLUS);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(MetaTokenType.STAR);
            result = result || parseTree.consumeToken(MetaTokenType.PLUS);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
