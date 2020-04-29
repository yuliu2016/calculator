package org.fugalang.core.grammar.pgen;

// repeat_rule: 'sub_rule' ['*' | '+']
public class RepeatRule {
    public final SubRule subRule;
    public final RepeatRule2Group repeatRule2Group;

    public RepeatRule(
            SubRule subRule,
            RepeatRule2Group repeatRule2Group
    ) {
        this.subRule = subRule;
        this.repeatRule2Group = repeatRule2Group;
    }

    // '*' | '+'
    public static class RepeatRule2Group {
        public final boolean isTokenStar;
        public final boolean isTokenPlus;

        public RepeatRule2Group(
                boolean isTokenStar,
                boolean isTokenPlus
        ) {
            this.isTokenStar = isTokenStar;
            this.isTokenPlus = isTokenPlus;
        }
    }
}
