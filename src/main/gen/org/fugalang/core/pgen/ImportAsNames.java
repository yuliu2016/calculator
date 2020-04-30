package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// import_as_names: 'import_as_name' (',' 'import_as_name')* [',']
public final class ImportAsNames extends ConjunctionRule {
    private final ImportAsName importAsName;
    private final List<ImportAsNames2> importAsNames2List;
    private final boolean isTokenComma;

    public ImportAsNames(
            ImportAsName importAsName,
            List<ImportAsNames2> importAsNames2List,
            boolean isTokenComma
    ) {
        this.importAsName = importAsName;
        this.importAsNames2List = importAsNames2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("importAsName", importAsName);
        addRequired("importAsNames2List", importAsNames2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public ImportAsName importAsName() {
        return importAsName;
    }

    public List<ImportAsNames2> importAsNames2List() {
        return importAsNames2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'import_as_name'
    public static final class ImportAsNames2 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ImportAsName importAsName;

        public ImportAsNames2(
                boolean isTokenComma,
                ImportAsName importAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.importAsName = importAsName;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma);
            addRequired("importAsName", importAsName);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public ImportAsName importAsName() {
            return importAsName;
        }
    }
}
