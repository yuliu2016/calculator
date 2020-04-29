package org.fugalang.core.pgen;

import java.util.List;

// dotted_name: 'NAME' ('.' 'NAME')*
public class DottedName {
    public final Object name;
    public final List<DottedName2Group> dottedName2GroupList;

    public DottedName(
            Object name,
            List<DottedName2Group> dottedName2GroupList
    ) {
        this.name = name;
        this.dottedName2GroupList = dottedName2GroupList;
    }

    // '.' 'NAME'
    public static class DottedName2Group {
        public final boolean isTokenDot;
        public final Object name;

        public DottedName2Group(
                boolean isTokenDot,
                Object name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;
        }
    }
}
