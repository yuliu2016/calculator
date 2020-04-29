package org.fugalang.core.grammar.pgen;

import java.util.List;

// or_rule: 'and_rule' ('|' 'and_rule')*
public class OrRule {
    public final AndRule andRule;
    public final List<OrRule2Group> orRule2GroupList;

    public OrRule(
            AndRule andRule,
            List<OrRule2Group> orRule2GroupList
    ) {
        this.andRule = andRule;
        this.orRule2GroupList = orRule2GroupList;
    }

    // '|' 'and_rule'
    public static class OrRule2Group {
        public final boolean isTokenOr;
        public final AndRule andRule;

        public OrRule2Group(
                boolean isTokenOr,
                AndRule andRule
        ) {
            this.isTokenOr = isTokenOr;
            this.andRule = andRule;
        }
    }
}
