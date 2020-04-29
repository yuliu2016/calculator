package org.fugalang.core.pgen;

// augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
public class Augassign {
    public final boolean isTokenPlusAssign;
    public final boolean isTokenMinusAssign;
    public final boolean isTokenTimesAssign;
    public final boolean isTokenMatrixTimesAssign;
    public final boolean isTokenDivAssign;
    public final boolean isTokenModulusAssign;
    public final boolean isTokenBitAndAssign;
    public final boolean isTokenBitOrAssign;
    public final boolean isTokenBitXorAssign;
    public final boolean isTokenLshiftAssign;
    public final boolean isTokenRshiftAssign;
    public final boolean isTokenPowerAssign;
    public final boolean isTokenFloorDivAssign;

    public Augassign(
            boolean isTokenPlusAssign,
            boolean isTokenMinusAssign,
            boolean isTokenTimesAssign,
            boolean isTokenMatrixTimesAssign,
            boolean isTokenDivAssign,
            boolean isTokenModulusAssign,
            boolean isTokenBitAndAssign,
            boolean isTokenBitOrAssign,
            boolean isTokenBitXorAssign,
            boolean isTokenLshiftAssign,
            boolean isTokenRshiftAssign,
            boolean isTokenPowerAssign,
            boolean isTokenFloorDivAssign
    ) {
        this.isTokenPlusAssign = isTokenPlusAssign;
        this.isTokenMinusAssign = isTokenMinusAssign;
        this.isTokenTimesAssign = isTokenTimesAssign;
        this.isTokenMatrixTimesAssign = isTokenMatrixTimesAssign;
        this.isTokenDivAssign = isTokenDivAssign;
        this.isTokenModulusAssign = isTokenModulusAssign;
        this.isTokenBitAndAssign = isTokenBitAndAssign;
        this.isTokenBitOrAssign = isTokenBitOrAssign;
        this.isTokenBitXorAssign = isTokenBitXorAssign;
        this.isTokenLshiftAssign = isTokenLshiftAssign;
        this.isTokenRshiftAssign = isTokenRshiftAssign;
        this.isTokenPowerAssign = isTokenPowerAssign;
        this.isTokenFloorDivAssign = isTokenFloorDivAssign;
    }
}
