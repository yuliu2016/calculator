package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// dotted_name: 'NAME' ('.' 'NAME')*
public final class DottedName extends ConjunctionRule {
    private final String name;
    private final List<DottedName2Group> dottedName2GroupList;

    public DottedName(
            String name,
            List<DottedName2Group> dottedName2GroupList
    ) {
        this.name = name;
        this.dottedName2GroupList = dottedName2GroupList;

        addRequired("name", name);
        addRequired("dottedName2GroupList", dottedName2GroupList);
    }

    public String name() {
        return name;
    }

    public List<DottedName2Group> dottedName2GroupList() {
        return dottedName2GroupList;
    }

    // '.' 'NAME'
    public static final class DottedName2Group extends ConjunctionRule {
        private final boolean isTokenDot;
        private final String name;

        public DottedName2Group(
                boolean isTokenDot,
                String name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;

            addRequired("isTokenDot", isTokenDot);
            addRequired("name", name);
        }

        public boolean isTokenDot() {
            return isTokenDot;
        }

        public String name() {
            return name;
        }
    }
}
