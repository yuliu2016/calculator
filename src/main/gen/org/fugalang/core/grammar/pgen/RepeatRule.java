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

    public SubRule subRule() {
        return SubRule.of(getItem(0));
    }

    public RepeatRule2 repeatRule2() {
        return RepeatRule2.of(getItem(1));
    }

    public boolean hasRepeatRule2() {
        return hasItemOfRule(1, RepeatRule2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = SubRule.parse(parseTree, level + 1);
        if (result) RepeatRule2.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public String star() {
            return getItemOfType(0, MetaTokenType.STAR);
        }

        public boolean hasStar() {
            return hasItemOfType(0, MetaTokenType.STAR);
        }

        public String plus() {
            return getItemOfType(1, MetaTokenType.PLUS);
        }

        public boolean hasPlus() {
            return hasItemOfType(1, MetaTokenType.PLUS);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(MetaTokenType.STAR);
            result = result || parseTree.consumeToken(MetaTokenType.PLUS);

            parseTree.exit(result);
            return result;
        }
    }
}
