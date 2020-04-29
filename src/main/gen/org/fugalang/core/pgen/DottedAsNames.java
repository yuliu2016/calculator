package org.fugalang.core.pgen;

import java.util.List;

// dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')* [',']
public class DottedAsNames {
    public final DottedAsName dottedAsName;
    public final List<DottedAsNames2Group> dottedAsNames2GroupList;
    public final boolean isTokenComma;

    public DottedAsNames(
            DottedAsName dottedAsName,
            List<DottedAsNames2Group> dottedAsNames2GroupList,
            boolean isTokenComma
    ) {
        this.dottedAsName = dottedAsName;
        this.dottedAsNames2GroupList = dottedAsNames2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // ',' 'dotted_as_name'
    public static class DottedAsNames2Group {
        public final boolean isTokenComma;
        public final DottedAsName dottedAsName;

        public DottedAsNames2Group(
                boolean isTokenComma,
                DottedAsName dottedAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.dottedAsName = dottedAsName;
        }
    }
}
