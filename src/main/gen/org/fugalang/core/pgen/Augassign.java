package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
public final class Augassign extends DisjunctionRule {
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

    @Override
    protected void buildRule() {
        addChoice("isTokenPlusAssign", isTokenPlusAssign);
        addChoice("isTokenMinusAssign", isTokenMinusAssign);
        addChoice("isTokenTimesAssign", isTokenTimesAssign);
        addChoice("isTokenMatrixTimesAssign", isTokenMatrixTimesAssign);
        addChoice("isTokenDivAssign", isTokenDivAssign);
        addChoice("isTokenModulusAssign", isTokenModulusAssign);
        addChoice("isTokenBitAndAssign", isTokenBitAndAssign);
        addChoice("isTokenBitOrAssign", isTokenBitOrAssign);
        addChoice("isTokenBitXorAssign", isTokenBitXorAssign);
        addChoice("isTokenLshiftAssign", isTokenLshiftAssign);
        addChoice("isTokenRshiftAssign", isTokenRshiftAssign);
        addChoice("isTokenPowerAssign", isTokenPowerAssign);
        addChoice("isTokenFloorDivAssign", isTokenFloorDivAssign);
    }

    public boolean isTokenPlusAssign() {
        return isTokenPlusAssign;
    }

    public boolean isTokenMinusAssign() {
        return isTokenMinusAssign;
    }

    public boolean isTokenTimesAssign() {
        return isTokenTimesAssign;
    }

    public boolean isTokenMatrixTimesAssign() {
        return isTokenMatrixTimesAssign;
    }

    public boolean isTokenDivAssign() {
        return isTokenDivAssign;
    }

    public boolean isTokenModulusAssign() {
        return isTokenModulusAssign;
    }

    public boolean isTokenBitAndAssign() {
        return isTokenBitAndAssign;
    }

    public boolean isTokenBitOrAssign() {
        return isTokenBitOrAssign;
    }

    public boolean isTokenBitXorAssign() {
        return isTokenBitXorAssign;
    }

    public boolean isTokenLshiftAssign() {
        return isTokenLshiftAssign;
    }

    public boolean isTokenRshiftAssign() {
        return isTokenRshiftAssign;
    }

    public boolean isTokenPowerAssign() {
        return isTokenPowerAssign;
    }

    public boolean isTokenFloorDivAssign() {
        return isTokenFloorDivAssign;
    }
}
