package org.fugalang.core.pgen;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public class Targets {
    public final Targets1Group targets1Group;
    public final Targets2Group targets2Group;
    public final boolean isTokenComma;

    public Targets(
            Targets1Group targets1Group,
            Targets2Group targets2Group,
            boolean isTokenComma
    ) {
        this.targets1Group = targets1Group;
        this.targets2Group = targets2Group;
        this.isTokenComma = isTokenComma;
    }
}
