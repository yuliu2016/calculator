package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// import_as_name: 'NAME' ['as' 'NAME']
public final class ImportAsName extends ConjunctionRule {
    private final String name;
    private final ImportAsName2Group importAsName2Group;

    public ImportAsName(
            String name,
            ImportAsName2Group importAsName2Group
    ) {
        this.name = name;
        this.importAsName2Group = importAsName2Group;

        addRequired("name", name);
        addOptional("importAsName2Group", importAsName2Group);
    }

    public String name() {
        return name;
    }

    public Optional<ImportAsName2Group> importAsName2Group() {
        return Optional.ofNullable(importAsName2Group);
    }

    // 'as' 'NAME'
    public static final class ImportAsName2Group extends ConjunctionRule {
        private final boolean isTokenAs;
        private final String name;

        public ImportAsName2Group(
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
