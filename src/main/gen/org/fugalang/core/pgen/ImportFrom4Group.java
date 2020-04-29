package org.fugalang.core.pgen;

// '*' | '(' 'import_as_names' ')' | 'import_as_names'
public class ImportFrom4Group {
    public final boolean isTokenTimes;
    public final ImportFrom4Group2 importFrom4Group2;
    public final ImportAsNames importAsNames;

    public ImportFrom4Group(
            boolean isTokenTimes,
            ImportFrom4Group2 importFrom4Group2,
            ImportAsNames importAsNames
    ) {
        this.isTokenTimes = isTokenTimes;
        this.importFrom4Group2 = importFrom4Group2;
        this.importAsNames = importAsNames;
    }
}
