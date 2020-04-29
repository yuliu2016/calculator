package org.fugalang.core.pgen;

// '(' 'import_as_names' ')'
public class ImportFrom4Group2 {
    public final boolean isTokenLpar;
    public final ImportAsNames importAsNames;
    public final boolean isTokenRpar;

    public ImportFrom4Group2(
            boolean isTokenLpar,
            ImportAsNames importAsNames,
            boolean isTokenRpar
    ) {
        this.isTokenLpar = isTokenLpar;
        this.importAsNames = importAsNames;
        this.isTokenRpar = isTokenRpar;
    }
}
