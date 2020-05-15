package org.fugalang.core.grammar.psi;

import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

@Deprecated
public class SingleRule implements TreeStringElem {
    public final String name;
    public final OrRule orRule;

    public SingleRule(String name, OrRule orRule) {
        this.name = name;
        this.orRule = orRule;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        builder.setName("rule")
                .addString(name)
                .addElem(orRule);
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
        return name + ": " + orRule.toString();
    }
}
