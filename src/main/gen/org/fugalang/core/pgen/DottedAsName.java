package org.fugalang.core.pgen;

// dotted_as_name: 'dotted_name' ['as' 'NAME']
public class DottedAsName {
    public final DottedName dottedName;
    public final DottedAsName2Group dottedAsName2Group;

    public DottedAsName(
            DottedName dottedName,
            DottedAsName2Group dottedAsName2Group
    ) {
        this.dottedName = dottedName;
        this.dottedAsName2Group = dottedAsName2Group;
    }
}
