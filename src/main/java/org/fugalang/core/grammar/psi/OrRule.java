package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

import java.util.List;

public class OrRule implements CSTPrintElem {
    public final AndRule andRule;

    public final List<AndRule> andRules;

    public OrRule(AndRule andRule, List<AndRule> andRules) {
        this.andRule = andRule;
        this.andRules = andRules;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("or")
                .addElem(andRule)
                .addElems(andRules);
    }
}