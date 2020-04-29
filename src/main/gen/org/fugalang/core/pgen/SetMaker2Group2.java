package org.fugalang.core.pgen;

import java.util.List;

// (',' ('expr' | 'star_expr'))* [',']
public class SetMaker2Group2 {
    public final List<SetMaker2Group21Group> setMaker2Group21GroupList;
    public final boolean isTokenComma;

    public SetMaker2Group2(
            List<SetMaker2Group21Group> setMaker2Group21GroupList,
            boolean isTokenComma
    ) {
        this.setMaker2Group21GroupList = setMaker2Group21GroupList;
        this.isTokenComma = isTokenComma;
    }
}
