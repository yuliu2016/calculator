package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// repeat_rule: 'sub_rule' ['*' | '+']
public final class RepeatRule extends ConjunctionRule {
    public static final String RULE_NAME = "repeat_rule";

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
        setExplicitName(RULE_NAME);
        addRequired("subRule", subRule);
        addOptional("repeatRule2", repeatRule2);
    }

    public SubRule subRule() {
        return subRule;
    }

    public Optional<RepeatRule2> repeatRule2() {
        return Optional.ofNullable(repeatRule2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = SubRule.parse(parseTree, level + 1);
        RepeatRule2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // '*' | '+'
    public static final class RepeatRule2 extends DisjunctionRule {
        public static final String RULE_NAME = "repeat_rule:2";

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
            setImpliedName(RULE_NAME);
            addChoice("isTokenStar", isTokenStar);
            addChoice("isTokenPlus", isTokenPlus);
        }

        public boolean isTokenStar() {
            return isTokenStar;
        }

        public boolean isTokenPlus() {
            return isTokenPlus;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("*");
            if (!result) result = parseTree.consumeTokenLiteral("+");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
