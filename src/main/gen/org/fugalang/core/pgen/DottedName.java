package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// dotted_name: 'NAME' ('.' 'NAME')*
public final class DottedName extends ConjunctionRule {
    public static final String RULE_NAME = "dotted_name";

    private final String name;
    private final List<DottedName2> dottedName2List;

    public DottedName(
            String name,
            List<DottedName2> dottedName2List
    ) {
        this.name = name;
        this.dottedName2List = dottedName2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("name", name);
        addRequired("dottedName2List", dottedName2List);
    }

    public String name() {
        return name;
    }

    public List<DottedName2> dottedName2List() {
        return dottedName2List;
    }

    // '.' 'NAME'
    public static final class DottedName2 extends ConjunctionRule {
        public static final String RULE_NAME = "dotted_name:2";

        private final boolean isTokenDot;
        private final String name;

        public DottedName2(
                boolean isTokenDot,
                String name
        ) {
            this.isTokenDot = isTokenDot;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
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
