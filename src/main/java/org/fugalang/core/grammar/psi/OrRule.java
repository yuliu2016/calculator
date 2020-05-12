package org.fugalang.core.grammar.psi;

import org.fugalang.core.grammar.util.FirstAndMore;
import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Deprecated
public class OrRule implements TreeStringElem {
    public final AndRule andRule;

    public final List<AndRule> andRules;

    public OrRule(AndRule andRule, List<AndRule> andRules) {
        this.andRule = Objects.requireNonNull(andRule);
        this.andRules = Objects.requireNonNull(andRules);
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        builder.setName("or")
                .addElem(andRule)
                .addElems(andRules);
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
        return andRule.toString() + andRules
                .stream()
                .map(rule -> " | " + rule.toString())
                .collect(Collectors.joining());
    }

    public Iterable<AndRule> allAndRules() {
        return FirstAndMore.of(andRule, andRules);
    }
}
