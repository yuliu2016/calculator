package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrRule implements CSTPrintElem {
    public final AndRule andRule;

    public final List<AndRule> andRules;

    public OrRule(AndRule andRule, List<AndRule> andRules) {
        this.andRule = Objects.requireNonNull(andRule);
        this.andRules = Objects.requireNonNull(andRules);
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("or")
                .addElem(andRule)
                .addElems(andRules);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return andRule.toSimpleString() + andRules
                .stream()
                .map(rule -> " | " + rule.toSimpleString())
                .collect(Collectors.joining());
    }
}
