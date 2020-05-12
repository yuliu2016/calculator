package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.TreeStringBuilder;
import org.fugalang.core.pprint.TreeStringElem;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class Rules implements TreeStringElem {
    public final List<SingleRule> rules;

    public Rules(List<SingleRule> rules) {
        this.rules = rules;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        builder.setName("rules").addElems(rules);
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
        return rules.stream().map(SingleRule::toString).collect(Collectors.joining("\n"));
    }
}
