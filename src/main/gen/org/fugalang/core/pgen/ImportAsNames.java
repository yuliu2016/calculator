package org.fugalang.core.pgen;

import java.util.List;

// import_as_names: 'import_as_name' (',' 'import_as_name')* [',']
public class ImportAsNames {
    public final ImportAsName importAsName;
    public final List<ImportAsNames2Group> importAsNames2GroupList;
    public final boolean isTokenComma;

    public ImportAsNames(
            ImportAsName importAsName,
            List<ImportAsNames2Group> importAsNames2GroupList,
            boolean isTokenComma
    ) {
        this.importAsName = importAsName;
        this.importAsNames2GroupList = importAsNames2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // ',' 'import_as_name'
    public static class ImportAsNames2Group {
        public final boolean isTokenComma;
        public final ImportAsName importAsName;

        public ImportAsNames2Group(
                boolean isTokenComma,
                ImportAsName importAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.importAsName = importAsName;
        }
    }
}
