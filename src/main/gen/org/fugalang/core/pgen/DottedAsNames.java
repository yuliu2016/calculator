package org.fugalang.core.pgen;

// dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')* [',']
public class DottedAsNames {
    public final DottedAsName dottedAsName;
    public final DottedAsNames2Group dottedAsNames2Group;
    public final boolean isTokenComma;

    public DottedAsNames(
            DottedAsName dottedAsName,
            DottedAsNames2Group dottedAsNames2Group,
            boolean isTokenComma
    ) {
        this.dottedAsName = dottedAsName;
        this.dottedAsNames2Group = dottedAsNames2Group;
        this.isTokenComma = isTokenComma;
    }
}
