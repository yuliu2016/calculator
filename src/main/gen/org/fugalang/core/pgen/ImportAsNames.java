package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// import_as_names: 'import_as_name' (',' 'import_as_name')* [',']
public final class ImportAsNames extends ConjunctionRule {
    private final ImportAsName importAsName;
    private final List<ImportAsNames2Group> importAsNames2GroupList;
    private final boolean isTokenComma;

    public ImportAsNames(
            ImportAsName importAsName,
            List<ImportAsNames2Group> importAsNames2GroupList,
            boolean isTokenComma
    ) {
        this.importAsName = importAsName;
        this.importAsNames2GroupList = importAsNames2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public ImportAsName getImportAsName() {
        return importAsName;
    }

    public List<ImportAsNames2Group> getImportAsNames2GroupList() {
        return importAsNames2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // ',' 'import_as_name'
    public static final class ImportAsNames2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ImportAsName importAsName;

        public ImportAsNames2Group(
                boolean isTokenComma,
                ImportAsName importAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.importAsName = importAsName;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public ImportAsName getImportAsName() {
            return importAsName;
        }
    }
}
