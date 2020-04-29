package org.fugalang.core.pgen;

// 'as' 'NAME'
public class DottedAsName2Group {
    public final boolean isTokenAs;
    public final Object name;

    public DottedAsName2Group(
            boolean isTokenAs,
            Object name
    ) {
        this.isTokenAs = isTokenAs;
        this.name = name;
    }
}
