package org.fugalang.core.grammar.psi;

import org.fugalang.core.pprint.CSTPrintBuilder;
import org.fugalang.core.pprint.CSTPrintElem;

public class RepeatRule implements CSTPrintElem {
    public final SubRule subRule;
    public final boolean tokenStar;
    public final boolean tokenPlus;

    public RepeatRule(SubRule subRule, boolean tokenStar, boolean tokenPlus) {
        this.subRule = subRule;
        this.tokenStar = tokenStar;
        this.tokenPlus = tokenPlus;
    }

    @Override
    public void buildString(CSTPrintBuilder builder) {
        builder.setName("repeat_rule");
        if (tokenPlus) builder.addString("+");
        else if (tokenStar) builder.addString("*");
    }
}
