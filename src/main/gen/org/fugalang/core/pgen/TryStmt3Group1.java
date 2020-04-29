package org.fugalang.core.pgen;

import java.util.List;

// ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite']
public class TryStmt3Group1 {
    public final TryStmt3Group11Group tryStmt3Group11Group;
    public final List<TryStmt3Group11Group> tryStmt3Group11GroupList;
    public final TryStmt3Group12Group tryStmt3Group12Group;
    public final TryStmt3Group13Group tryStmt3Group13Group;

    public TryStmt3Group1(
            TryStmt3Group11Group tryStmt3Group11Group,
            List<TryStmt3Group11Group> tryStmt3Group11GroupList,
            TryStmt3Group12Group tryStmt3Group12Group,
            TryStmt3Group13Group tryStmt3Group13Group
    ) {
        this.tryStmt3Group11Group = tryStmt3Group11Group;
        this.tryStmt3Group11GroupList = tryStmt3Group11GroupList;
        this.tryStmt3Group12Group = tryStmt3Group12Group;
        this.tryStmt3Group13Group = tryStmt3Group13Group;
    }
}
