package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')* [',']
public final class DottedAsNames extends ConjunctionRule {
    public static final String RULE_NAME = "dotted_as_names";

    private final DottedAsName dottedAsName;
    private final List<DottedAsNames2> dottedAsNames2List;
    private final boolean isTokenComma;

    public DottedAsNames(
            DottedAsName dottedAsName,
            List<DottedAsNames2> dottedAsNames2List,
            boolean isTokenComma
    ) {
        this.dottedAsName = dottedAsName;
        this.dottedAsNames2List = dottedAsNames2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("dottedAsName", dottedAsName);
        addRequired("dottedAsNames2List", dottedAsNames2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public DottedAsName dottedAsName() {
        return dottedAsName;
    }

    public List<DottedAsNames2> dottedAsNames2List() {
        return dottedAsNames2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'dotted_as_name'
    public static final class DottedAsNames2 extends ConjunctionRule {
        public static final String RULE_NAME = "dotted_as_names:2";

        private final boolean isTokenComma;
        private final DottedAsName dottedAsName;

        public DottedAsNames2(
                boolean isTokenComma,
                DottedAsName dottedAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.dottedAsName = dottedAsName;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("dottedAsName", dottedAsName);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public DottedAsName dottedAsName() {
            return dottedAsName;
        }
    }
}
