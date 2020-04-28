package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AndRule implements CSTPrintElem {
    public final RepeatRule repeatRule;
    public final List<RepeatRule> repeatRules;

    public AndRule(RepeatRule repeatRule, List<RepeatRule> repeatRules) {
        this.repeatRule = Objects.requireNonNull(repeatRule);
        this.repeatRules = Objects.requireNonNull(repeatRules);
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("and").addElem(repeatRule).addElems(repeatRules);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return repeatRule.toSimpleString() + repeatRules
                .stream()
                .map(rule -> " " + rule.toSimpleString())
                .collect(Collectors.joining());
    }
}
