package org.fugalang.core.pgen;

import java.util.List;

// '.'* 'dotted_name'
public class ImportFrom2Group1 {
    public final List<Boolean> isTokenDotList;
    public final DottedName dottedName;

    public ImportFrom2Group1(
            List<Boolean> isTokenDotList,
            DottedName dottedName
    ) {
        this.isTokenDotList = isTokenDotList;
        this.dottedName = dottedName;
    }
}
