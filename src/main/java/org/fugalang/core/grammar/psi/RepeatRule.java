package org.fugalang.core.grammar.psi;

import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

@Deprecated
public class RepeatRule implements TreeStringElem {
    public final SubRule subRule;
    private final boolean tokenStar;
    private final boolean tokenPlus;

    public final RepeatType type;

    public RepeatRule(SubRule subRule, boolean tokenStar, boolean tokenPlus) {
        this.subRule = subRule;
        this.tokenStar = tokenStar;
        this.tokenPlus = tokenPlus;

        if (tokenStar && tokenPlus) {
            throw new IllegalArgumentException("Cannot be Star and Plus at the same time");
        }

        type = tokenStar ? RepeatType.NoneOrMore : tokenPlus ? RepeatType.OnceOrMore : RepeatType.Once;
    }

    @Override
    public void buildString(TreeStringBuilder builder) {
        if (tokenPlus) builder.setName("repeat");
        else if (tokenStar) builder.setName("opt_repeat");
        else builder.setName("once");
        builder.addElem(subRule);
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
        return subRule + (tokenStar ? "*" : tokenPlus ? "+" : "");
    }

}
