package org.fugalang.core.pgen;

// ',' 'import_as_name'
public class ImportAsNames2Group {
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
