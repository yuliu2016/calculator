package org.fugalang.core.grammar.pgen;

import java.util.List;

// and_rule: 'repeat_rule' ('repeat_rule')*
public class AndRule {
    public final RepeatRule repeatRule;
    public final List<AndRule2Group> andRule2GroupList;

    public AndRule(
            RepeatRule repeatRule,
            List<AndRule2Group> andRule2GroupList
    ) {
        this.repeatRule = repeatRule;
        this.andRule2GroupList = andRule2GroupList;
    }

    // 'repeat_rule'
    public static class AndRule2Group {
        public final RepeatRule repeatRule;

        public AndRule2Group(
                RepeatRule repeatRule
        ) {
            this.repeatRule = repeatRule;
        }
    }
}
