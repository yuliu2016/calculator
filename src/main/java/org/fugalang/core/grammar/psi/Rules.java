package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;
import java.util.stream.Collectors;

public class Rules implements CSTPrintElem {
    public final List<SingleRule> rules;

    public Rules(List<SingleRule> rules) {
        this.rules = rules;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("rules").addElems(rules);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return rules.stream().map(SingleRule::toSimpleString).collect(Collectors.joining("\n"));
    }
}
