package org.fugalang.core.pgen;

// '.' 'NAME'
public class DottedName2Group {
    public final boolean isTokenDot;
    public final Object name;

    public DottedName2Group(
            boolean isTokenDot,
            Object name
    ) {
        this.isTokenDot = isTokenDot;
        this.name = name;
    }
}
