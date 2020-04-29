package org.fugalang.core.grammar.pgen;

// sub_rule: '(' 'or_rule' ')' | '[' 'or_rule' ']' | 'TOK'
public class SubRule {
    public final SubRule1 subRule1;
    public final SubRule2 subRule2;
    public final String token;

    public SubRule(
            SubRule1 subRule1,
            SubRule2 subRule2,
            String token
    ) {
        this.subRule1 = subRule1;
        this.subRule2 = subRule2;
        this.token = token;
    }

    // '(' 'or_rule' ')'
    public static class SubRule1 {
        public final boolean isTokenLpar;
        public final OrRule orRule;
        public final boolean isTokenRpar;

        public SubRule1(
                boolean isTokenLpar,
                OrRule orRule,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.orRule = orRule;
            this.isTokenRpar = isTokenRpar;
        }
    }

    // '[' 'or_rule' ']'
    public static class SubRule2 {
        public final boolean isTokenLsqb;
        public final OrRule orRule;
        public final boolean isTokenRsqb;

        public SubRule2(
                boolean isTokenLsqb,
                OrRule orRule,
                boolean isTokenRsqb
        ) {
            this.isTokenLsqb = isTokenLsqb;
            this.orRule = orRule;
            this.isTokenRsqb = isTokenRsqb;
        }
    }
}
