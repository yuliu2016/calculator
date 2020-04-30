package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// import_as_name: 'NAME' ['as' 'NAME']
public final class ImportAsName extends ConjunctionRule {
    private final String name;
    private final ImportAsName2 importAsName2;

    public ImportAsName(
            String name,
            ImportAsName2 importAsName2
    ) {
        this.name = name;
        this.importAsName2 = importAsName2;
    }

    @Override
    protected void buildRule() {
        addRequired("name", name);
        addOptional("importAsName2", importAsName2);
    }

    public String name() {
        return name;
    }

    public Optional<ImportAsName2> importAsName2() {
        return Optional.ofNullable(importAsName2);
    }

    // 'as' 'NAME'
    public static final class ImportAsName2 extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public ImportAsName2(
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
