package org.fugalang.core.grammar.psi;

public class RepeatRule {
    public final SubRule subRule;
    public final boolean tokenStar;
    public final boolean tokenPlus;

    public RepeatRule(SubRule subRule, boolean tokenStar, boolean tokenPlus) {
        this.subRule = subRule;
        this.tokenStar = tokenStar;
        this.tokenPlus = tokenPlus;
    }
}
