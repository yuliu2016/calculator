package org.fugalang.core.pgen;

// ('.')* 'dotted_name'
public class ImportFrom2Group1 {
    public final boolean isTokenDot;
    public final DottedName dottedName;

    public ImportFrom2Group1(
            boolean isTokenDot,
            DottedName dottedName
    ) {
        this.isTokenDot = isTokenDot;
        this.dottedName = dottedName;
    }
}
