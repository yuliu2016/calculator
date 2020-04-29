package org.fugalang.core.grammar.pgen;

// rules: 'single_rule'*
public class Rules {
    public final SingleRule singleRule;

    public Rules(
            SingleRule singleRule
    ) {
        this.singleRule = singleRule;
    }
}
