package org.fugalang.core.pgen;

import java.util.List;

// dotted_name: 'NAME' ('.' 'NAME')*
public class DottedName {
    public final Object name;
    public final List<DottedName2Group> dottedName2GroupList;

    public DottedName(
            Object name,
            List<DottedName2Group> dottedName2GroupList
    ) {
        this.name = name;
        this.dottedName2GroupList = dottedName2GroupList;
    }
}
