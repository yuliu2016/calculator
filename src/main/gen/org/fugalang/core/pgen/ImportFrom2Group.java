package org.fugalang.core.pgen;

import java.util.List;

// '.'* 'dotted_name' | '.'+
public class ImportFrom2Group {
    public final ImportFrom2Group1 importFrom2Group1;
    public final boolean isTokenDot;
    public final List<Boolean> isTokenDotList;

    public ImportFrom2Group(
            ImportFrom2Group1 importFrom2Group1,
            boolean isTokenDot,
            List<Boolean> isTokenDotList
    ) {
        this.importFrom2Group1 = importFrom2Group1;
        this.isTokenDot = isTokenDot;
        this.isTokenDotList = isTokenDotList;
    }
}
