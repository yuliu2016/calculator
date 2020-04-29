package org.fugalang.core.pgen;

// dotted_name: 'NAME' ('.' 'NAME')*
public class DottedName {
    public final Object name;
    public final DottedName2Group dottedName2Group;

    public DottedName(
            Object name,
            DottedName2Group dottedName2Group
    ) {
        this.name = name;
        this.dottedName2Group = dottedName2Group;
    }
}
