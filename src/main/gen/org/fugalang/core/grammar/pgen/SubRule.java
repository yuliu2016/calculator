package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// sub_rule: '(' 'or_rule' ')' | '[' 'or_rule' ']' | 'TOK'
public final class SubRule extends DisjunctionRule {
    public static final String RULE_NAME = "sub_rule";

    private final SubRule1 subRule1;
    private final SubRule2 subRule2;
    private final String token;

    public SubRule(
            SubRule1 subRule1,
            SubRule2 subRule2,
            String token
    ) {
        this.subRule1 = subRule1;
        this.subRule2 = subRule2;
        this.token = token;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("subRule1", subRule1);
        addChoice("subRule2", subRule2);
        addChoice("token", token);
    }

    public SubRule1 subRule1() {
        return subRule1;
    }

    public SubRule2 subRule2() {
        return subRule2;
    }

    public String token() {
        return token;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = SubRule1.parse(parseTree, level + 1);
        if (!result) result = SubRule2.parse(parseTree, level + 1);
        if (!result) result = parseTree.consumeTokenType("TOK");

        parseTree.exit(level, marker, result);
        return result;
    }

    // '(' 'or_rule' ')'
    public static final class SubRule1 extends ConjunctionRule {
        public static final String RULE_NAME = "sub_rule:1";

        private final boolean isTokenLpar;
        private final OrRule orRule;
        private final boolean isTokenRpar;

        public SubRule1(
                boolean isTokenLpar,
                OrRule orRule,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.orRule = orRule;
            this.isTokenRpar = isTokenRpar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLpar", isTokenLpar);
            addRequired("orRule", orRule);
            addRequired("isTokenRpar", isTokenRpar);
        }

        public boolean isTokenLpar() {
            return isTokenLpar;
        }

        public OrRule orRule() {
            return orRule;
        }

        public boolean isTokenRpar() {
            return isTokenRpar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '[' 'or_rule' ']'
    public static final class SubRule2 extends ConjunctionRule {
        public static final String RULE_NAME = "sub_rule:2";

        private final boolean isTokenLsqb;
        private final OrRule orRule;
        private final boolean isTokenRsqb;

        public SubRule2(
                boolean isTokenLsqb,
                OrRule orRule,
                boolean isTokenRsqb
        ) {
            this.isTokenLsqb = isTokenLsqb;
            this.orRule = orRule;
            this.isTokenRsqb = isTokenRsqb;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLsqb", isTokenLsqb);
            addRequired("orRule", orRule);
            addRequired("isTokenRsqb", isTokenRsqb);
        }

        public boolean isTokenLsqb() {
            return isTokenLsqb;
        }

        public OrRule orRule() {
            return orRule;
        }

        public boolean isTokenRsqb() {
            return isTokenRsqb;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("[");
            result = result && OrRule.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
