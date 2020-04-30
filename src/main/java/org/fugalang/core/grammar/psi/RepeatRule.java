package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

public class RepeatRule implements CSTPrintElem {
    public final SubRule subRule;
    private final boolean tokenStar;
    private final boolean tokenPlus;

    public final Type type;

    public RepeatRule(SubRule subRule, boolean tokenStar, boolean tokenPlus) {
        this.subRule = subRule;
        this.tokenStar = tokenStar;
        this.tokenPlus = tokenPlus;

        if (tokenStar && tokenPlus) {
            throw new IllegalArgumentException("Cannot be Star and Plus at the same time");
        }

        type = tokenStar ? Type.NoneOrMore : tokenPlus ? Type.OnceOrMore : Type.Once;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        if (tokenPlus) builder.setName("repeat");
        else if (tokenStar) builder.setName("opt_repeat");
        else builder.setName("once");
        builder.addElem(subRule);
    }

    @Override
    public String toString() {
        return toSimpleString();
    }

    public String toSimpleString() {
        return subRule + (tokenStar ? "*" : tokenPlus ? "+" : "");
    }

    public enum Type {
        Once,
        OnceOrMore,
        NoneOrMore
    }
}
