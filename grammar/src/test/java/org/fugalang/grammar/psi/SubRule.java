package org.fugalang.grammar.psi;

import org.fugalang.grammar.common.SubRuleType;
import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

@Deprecated
public class SubRule implements TreeStringElem {
    public final OrRule groupedOrRule;
    public final OrRule optionalOrRule;
    public final String token;

    public final SubRuleType type;

    public SubRule(OrRule groupedOrRule, OrRule optionalOrRule, String token) {
        this.groupedOrRule = groupedOrRule;
        this.optionalOrRule = optionalOrRule;
        this.token = token;

        if (groupedOrRule != null) {
            if (optionalOrRule == null && token == null) {
                type = SubRuleType.Group;
            } else {
                throw new IllegalArgumentException("More than one non-null value");
            }
        } else if (optionalOrRule != null) {
            if (token == null) {
                type = SubRuleType.Optional;
            } else {
                throw new IllegalArgumentException("More than one non-null value");
            }
        } else {
            type = SubRuleType.Token;
        }
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        if (groupedOrRule != null)
            builder.setName("group").addElem(groupedOrRule);
        else if (optionalOrRule != null)
            builder.setName("opt").addElem(optionalOrRule);
        else if (token != null) builder.setName("token").addString(token);
        else throw new IllegalStateException();
    }

    private String str = null;

    @Override
    public String toString() {
        if (str == null) {
            str = toSimpleString();
        }
        return str;
    }

    public String toSimpleString() {
        return groupedOrRule != null ?
                "(" + groupedOrRule.toString() + ")" :
                optionalOrRule != null ?
                        "[" + optionalOrRule.toString() + "]" :
                        "'" + token + "'";
    }

}
