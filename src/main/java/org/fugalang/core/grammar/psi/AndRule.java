package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;

public class AndRule implements CSTPrintElem {
    public final RepeatRule repeatRule;
    public final List<RepeatRule> repeatRules;

    public AndRule(RepeatRule repeatRule, List<RepeatRule> repeatRules) {
        this.repeatRule = repeatRule;
        this.repeatRules = repeatRules;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("and_rule").addElem(repeatRule).addElems(repeatRules);
    }
}
