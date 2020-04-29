package org.fugalang.core.pgen;

// '**' 'factor'
public class Power2Group {
    public final boolean isTokenPower;
    public final Factor factor;

    public Power2Group(
            boolean isTokenPower,
            Factor factor
    ) {
        this.isTokenPower = isTokenPower;
        this.factor = factor;
    }
}
