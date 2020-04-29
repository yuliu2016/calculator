package org.fugalang.core.pgen;

// ('+' | '-' | '~') 'factor'
public class Factor1 {
    public final Factor11Group factor11Group;
    public final Factor factor;

    public Factor1(
            Factor11Group factor11Group,
            Factor factor
    ) {
        this.factor11Group = factor11Group;
        this.factor = factor;
    }
}
