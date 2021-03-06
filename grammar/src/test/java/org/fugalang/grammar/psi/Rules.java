package org.fugalang.grammar.psi;

import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

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
        builder.setName("rules");
        rules.forEach(builder::addElem);
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
