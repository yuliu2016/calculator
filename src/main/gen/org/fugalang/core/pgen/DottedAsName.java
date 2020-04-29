package org.fugalang.core.pgen;

import java.util.Optional;

// dotted_as_name: 'dotted_name' ['as' 'NAME']
public class DottedAsName {
    private final DottedName dottedName;
    private final DottedAsName2Group dottedAsName2Group;

    public DottedAsName(
            DottedName dottedName,
            DottedAsName2Group dottedAsName2Group
    ) {
        this.dottedName = dottedName;
        this.dottedAsName2Group = dottedAsName2Group;
    }

    public DottedName getDottedName() {
        return dottedName;
    }

    public Optional<DottedAsName2Group> getDottedAsName2Group() {
        return Optional.ofNullable(dottedAsName2Group);
    }

    // 'as' 'NAME'
    public static class DottedAsName2Group {
        private final boolean isTokenAs;
        private final Object name;

        public DottedAsName2Group(
                boolean isTokenAs,
                Object name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        public boolean getIsTokenAs() {
            return isTokenAs;
        }

        public Object getName() {
            return name;
        }
    }
}
