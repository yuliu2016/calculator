package org.fugalang.core.pgen;

import java.util.List;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public class Targets {
    public final Targets1Group targets1Group;
    public final List<Targets2Group> targets2GroupList;
    public final boolean isTokenComma;

    public Targets(
            Targets1Group targets1Group,
            List<Targets2Group> targets2GroupList,
            boolean isTokenComma
    ) {
        this.targets1Group = targets1Group;
        this.targets2GroupList = targets2GroupList;
        this.isTokenComma = isTokenComma;
    }
}
