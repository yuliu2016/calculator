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
}
