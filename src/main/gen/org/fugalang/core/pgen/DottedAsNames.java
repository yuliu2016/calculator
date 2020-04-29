package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')* [',']
public final class DottedAsNames extends ConjunctionRule {
    private final DottedAsName dottedAsName;
    private final List<DottedAsNames2Group> dottedAsNames2GroupList;
    private final boolean isTokenComma;

    public DottedAsNames(
            DottedAsName dottedAsName,
            List<DottedAsNames2Group> dottedAsNames2GroupList,
            boolean isTokenComma
    ) {
        this.dottedAsName = dottedAsName;
        this.dottedAsNames2GroupList = dottedAsNames2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public DottedAsName getDottedAsName() {
        return dottedAsName;
    }

    public List<DottedAsNames2Group> getDottedAsNames2GroupList() {
        return dottedAsNames2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // ',' 'dotted_as_name'
    public static final class DottedAsNames2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final DottedAsName dottedAsName;

        public DottedAsNames2Group(
                boolean isTokenComma,
                DottedAsName dottedAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.dottedAsName = dottedAsName;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public DottedAsName getDottedAsName() {
            return dottedAsName;
        }
    }
}
