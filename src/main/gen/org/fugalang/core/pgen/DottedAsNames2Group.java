package org.fugalang.core.pgen;

// ',' 'dotted_as_name'
public class DottedAsNames2Group {
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
