package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

public class SingleRule implements CSTPrintElem {
    public final String name;
    public final OrRule orRule;

    public SingleRule(String name, OrRule orRule) {
        this.name = name;
        this.orRule = orRule;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("rule")
                .addString(name)
                .addElem(orRule);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return name + ": " + orRule.toSimpleString();
    }
}
