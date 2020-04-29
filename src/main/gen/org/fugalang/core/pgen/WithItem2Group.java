package org.fugalang.core.pgen;

// 'as' 'NAME'
public class WithItem2Group {
    public final boolean isTokenAs;
    public final Object name;

    public WithItem2Group(
            boolean isTokenAs,
            Object name
    ) {
        this.isTokenAs = isTokenAs;
        this.name = name;
    }
}
