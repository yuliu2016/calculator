package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.Optional;

/**
 * repeat_rule: 'sub_rule' ['*' | '+']
 */
public final class RepeatRule extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("repeat_rule", RuleType.Conjunction, true);

    private final SubRule subRule;
    private final RepeatRule2 repeatRule2;

    public RepeatRule(
            SubRule subRule,
            RepeatRule2 repeatRule2
    ) {
        this.subRule = subRule;
        this.repeatRule2 = repeatRule2;
    }

    @Override
    protected void buildRule() {
        addRequired("subRule", subRule());
        addOptional("repeatRule2", repeatRule2());
    }

    public SubRule subRule() {
        return subRule;
    }

    public RepeatRule2 repeatRule2() {
        return repeatRule2;
    }

    public boolean hasRepeatRule2() {
        return repeatRule2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SubRule.parse(parseTree, level + 1);
        RepeatRule2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '*' | '+'
     */
    public static final class RepeatRule2 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("repeat_rule:2", RuleType.Disjunction, false);

        private final boolean isTokenStar;
        private final boolean isTokenPlus;

        public RepeatRule2(
                boolean isTokenStar,
                boolean isTokenPlus
        ) {
            this.isTokenStar = isTokenStar;
            this.isTokenPlus = isTokenPlus;
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenStar", isTokenStar());
            addChoice("isTokenPlus", isTokenPlus());
        }

        public boolean isTokenStar() {
            return isTokenStar;
        }

        public boolean isTokenPlus() {
            return isTokenPlus;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("*");
            result = result || parseTree.consumeTokenLiteral("+");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
