package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;

public class Rules implements CSTPrintElem {
    public final List<SingleRule> rules;

    public Rules(List<SingleRule> rules) {
        this.rules = rules;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("rules").addElems(rules);
    }
}
