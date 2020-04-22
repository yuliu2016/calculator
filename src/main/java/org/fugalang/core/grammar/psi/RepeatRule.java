package org.fugalang.core.grammar.psi;

public class RepeatRule {
    public final SubRule subRule;
    public final String tokenPlus;
    public final String tokenMinus;

    public RepeatRule(SubRule subRule, String tokenPlus, String tokenMinus) {
        this.subRule = subRule;
        this.tokenPlus = tokenPlus;
        this.tokenMinus = tokenMinus;
    }
}
