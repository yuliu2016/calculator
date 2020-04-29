package org.fugalang.core.pgen;

// conjunction: 'inversion' ('and' 'inversion')*
public class Conjunction {
    public final Inversion inversion;
    public final Conjunction2Group conjunction2Group;

    public Conjunction(
            Inversion inversion,
            Conjunction2Group conjunction2Group
    ) {
        this.inversion = inversion;
        this.conjunction2Group = conjunction2Group;
    }
}
