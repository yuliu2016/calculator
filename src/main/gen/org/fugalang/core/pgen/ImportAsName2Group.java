package org.fugalang.core.pgen;

// 'as' 'NAME'
public class ImportAsName2Group {
    public final boolean isTokenAs;
    public final Object name;

    public ImportAsName2Group(
            boolean isTokenAs,
            Object name
    ) {
        this.isTokenAs = isTokenAs;
        this.name = name;
    }
}
