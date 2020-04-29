package org.fugalang.core.pgen;

// '*' | '@' | '/' | '%' | '//'
public class Term2Group1Group {
    public final boolean isTokenTimes;
    public final boolean isTokenMatrixTimes;
    public final boolean isTokenDiv;
    public final boolean isTokenModulus;
    public final boolean isTokenFloorDiv;

    public Term2Group1Group(
            boolean isTokenTimes,
            boolean isTokenMatrixTimes,
            boolean isTokenDiv,
            boolean isTokenModulus,
            boolean isTokenFloorDiv
    ) {
        this.isTokenTimes = isTokenTimes;
        this.isTokenMatrixTimes = isTokenMatrixTimes;
        this.isTokenDiv = isTokenDiv;
        this.isTokenModulus = isTokenModulus;
        this.isTokenFloorDiv = isTokenFloorDiv;
    }
}
