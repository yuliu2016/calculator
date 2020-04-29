package org.fugalang.core.pgen;

// import_as_names: 'import_as_name' (',' 'import_as_name')* [',']
public class ImportAsNames {
    public final ImportAsName importAsName;
    public final ImportAsNames2Group importAsNames2Group;
    public final boolean isTokenComma;

    public ImportAsNames(
            ImportAsName importAsName,
            ImportAsNames2Group importAsNames2Group,
            boolean isTokenComma
    ) {
        this.importAsName = importAsName;
        this.importAsNames2Group = importAsNames2Group;
        this.isTokenComma = isTokenComma;
    }
}
