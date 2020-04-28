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
            builder.setName("group").addElem(groupedOrRule);
        else if (optionalOrRule != null)
            builder.setName("opt").addElem(optionalOrRule);
        else if (token != null) builder.setName("token").addString(token);
        else throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return groupedOrRule != null ?
                "(" + groupedOrRule.toSimpleString() + ")" :
                optionalOrRule != null ?
                        "[" + optionalOrRule.toSimpleString() + "]" :
                        "'" + token + "'";
    }
}
