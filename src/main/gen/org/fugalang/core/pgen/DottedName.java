package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// dotted_name: 'NAME' ('.' 'NAME')*
public final class DottedName extends ConjunctionRule {
    private final Object name;
    private final List<DottedName2Group> dottedName2GroupList;

    public DottedName(
            Object name,
            List<DottedName2Group> dottedName2GroupList
    ) {
        this.name = name;
        this.dottedName2GroupList = dottedName2GroupList;
    }

    public Object getName() {
        return name;
    }

    public List<DottedName2Group> getDottedName2GroupList() {
        return dottedName2GroupList;
    }

    // '.' 'NAME'
    public static final class DottedName2Group extends ConjunctionRule {
        private final boolean isTokenDot;
        private final Object name;

        public DottedName2Group(
                boolean isTokenDot,
                Object name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;
        }

        public boolean getIsTokenDot() {
            return isTokenDot;
        }

        public Object getName() {
            return name;
        }
    }
}
