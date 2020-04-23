package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

public class SubRule implements CSTPrintElem {
    public final OrRule groupedOrRule;
    public final OrRule optionalOrRule;
    public final String token;

    public SubRule(OrRule groupedOrRule, OrRule optionalOrRule, String token) {
        this.groupedOrRule = groupedOrRule;
        this.optionalOrRule = optionalOrRule;
        this.token = token;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        if (groupedOrRule != null)
            builder.setName("sub_rule_group").addElem(groupedOrRule);
        else if (optionalOrRule != null)
            builder.setName("sub_rule_opt").addElem(optionalOrRule);
        else if (token != null) builder.setName("sub_rule").addString(token);
        else throw new IllegalStateException();
    }
}
