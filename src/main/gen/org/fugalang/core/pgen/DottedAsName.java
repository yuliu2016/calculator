package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// dotted_as_name: 'dotted_name' ['as' 'NAME']
public final class DottedAsName extends ConjunctionRule {
    private final DottedName dottedName;
    private final DottedAsName2 dottedAsName2;

    public DottedAsName(
            DottedName dottedName,
            DottedAsName2 dottedAsName2
    ) {
        this.dottedName = dottedName;
        this.dottedAsName2 = dottedAsName2;
    }

    @Override
    protected void buildRule() {
        addRequired("dottedName", dottedName);
        addOptional("dottedAsName2", dottedAsName2);
    }

    public DottedName dottedName() {
        return dottedName;
    }

    public Optional<DottedAsName2> dottedAsName2() {
        return Optional.ofNullable(dottedAsName2);
    }

    // 'as' 'NAME'
    public static final class DottedAsName2 extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public DottedAsName2(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
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
