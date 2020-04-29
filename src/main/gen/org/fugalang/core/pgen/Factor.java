package org.fugalang.core.pgen;

// factor: ('+' | '-' | '~') 'factor' | 'power'
public class Factor {
    public final Factor1 factor1;
    public final Power power;

    public Factor(
            Factor1 factor1,
            Power power
    ) {
        this.factor1 = factor1;
        this.power = power;
    }
}
