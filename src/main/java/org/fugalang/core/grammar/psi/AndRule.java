package org.fugalang.core.grammar.psi;

import org.fugalang.core.grammar.util.FirstAndMore;
import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AndRule implements TreeStringElem {
    public final RepeatRule repeatRule;
    public final List<RepeatRule> repeatRules;

    public AndRule(RepeatRule repeatRule, List<RepeatRule> repeatRules) {
        this.repeatRule = Objects.requireNonNull(repeatRule);
        this.repeatRules = Objects.requireNonNull(repeatRules);
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        builder.setName("and").addElem(repeatRule).addElems(repeatRules);
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
        return repeatRule.toString() + repeatRules
                .stream()
                .map(rule -> " " + rule.toString())
                .collect(Collectors.joining());
    }

    public Iterable<RepeatRule> allRepeatRules() {
        return FirstAndMore.of(repeatRule, repeatRules);
    }
}
