package org.fugalang.core.pgen;

// augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
public class Augassign {
    private final boolean isTokenPlusAssign;
    private final boolean isTokenMinusAssign;
    private final boolean isTokenTimesAssign;
    private final boolean isTokenMatrixTimesAssign;
    private final boolean isTokenDivAssign;
    private final boolean isTokenModulusAssign;
    private final boolean isTokenBitAndAssign;
    private final boolean isTokenBitOrAssign;
    private final boolean isTokenBitXorAssign;
    private final boolean isTokenLshiftAssign;
    private final boolean isTokenRshiftAssign;
    private final boolean isTokenPowerAssign;
    private final boolean isTokenFloorDivAssign;

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

    public boolean getIsTokenPlusAssign() {
        return isTokenPlusAssign;
    }

    public boolean getIsTokenMinusAssign() {
        return isTokenMinusAssign;
    }

    public boolean getIsTokenTimesAssign() {
        return isTokenTimesAssign;
    }

    public boolean getIsTokenMatrixTimesAssign() {
        return isTokenMatrixTimesAssign;
    }

    public boolean getIsTokenDivAssign() {
        return isTokenDivAssign;
    }

    public boolean getIsTokenModulusAssign() {
        return isTokenModulusAssign;
    }

    public boolean getIsTokenBitAndAssign() {
        return isTokenBitAndAssign;
    }

    public boolean getIsTokenBitOrAssign() {
        return isTokenBitOrAssign;
    }

    public boolean getIsTokenBitXorAssign() {
        return isTokenBitXorAssign;
    }

    public boolean getIsTokenLshiftAssign() {
        return isTokenLshiftAssign;
    }

    public boolean getIsTokenRshiftAssign() {
        return isTokenRshiftAssign;
    }

    public boolean getIsTokenPowerAssign() {
        return isTokenPowerAssign;
    }

    public boolean getIsTokenFloorDivAssign() {
        return isTokenFloorDivAssign;
    }
}
