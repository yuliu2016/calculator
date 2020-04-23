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
        builder.setName("sub_rule");
        if (groupedOrRule != null)
            builder.addString("grouped").addElem(optionalOrRule);
        else if (optionalOrRule != null)
            builder.addString("optional").addElem(optionalOrRule);
        else if (token != null) builder.addString(token);
        else throw new IllegalStateException();
    }
}
