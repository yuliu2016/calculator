package org.fugalang.core.pgen;

// ('.')* 'dotted_name' | ('.')+
public class ImportFrom2Group {
    public final ImportFrom2Group1 importFrom2Group1;
    public final boolean isTokenDot;

    public ImportFrom2Group(
            ImportFrom2Group1 importFrom2Group1,
            boolean isTokenDot
    ) {
        this.importFrom2Group1 = importFrom2Group1;
        this.isTokenDot = isTokenDot;
    }
}
