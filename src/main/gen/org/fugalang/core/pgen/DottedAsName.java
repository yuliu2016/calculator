package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// dotted_as_name: 'dotted_name' ['as' 'NAME']
public final class DottedAsName extends ConjunctionRule {
    private final DottedName dottedName;
    private final DottedAsName2Group dottedAsName2Group;

    public DottedAsName(
            DottedName dottedName,
            DottedAsName2Group dottedAsName2Group
    ) {
        this.dottedName = dottedName;
        this.dottedAsName2Group = dottedAsName2Group;

        addRequired("dottedName", dottedName);
        addOptional("dottedAsName2Group", dottedAsName2Group);
    }

    public DottedName dottedName() {
        return dottedName;
    }

    public Optional<DottedAsName2Group> dottedAsName2Group() {
        return Optional.ofNullable(dottedAsName2Group);
    }

    // 'as' 'NAME'
    public static final class DottedAsName2Group extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public DottedAsName2Group(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;

            addRequired("isTokenAs", isTokenAs);
            addRequired("name", name);
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }
    }
}
