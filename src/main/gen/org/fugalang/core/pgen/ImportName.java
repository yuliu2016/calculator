package org.fugalang.core.pgen;

// import_name: 'import' 'dotted_as_names'
public class ImportName {
    private final boolean isTokenImport;
    private final DottedAsNames dottedAsNames;

    public ImportName(
            boolean isTokenImport,
            DottedAsNames dottedAsNames
    ) {
        this.isTokenImport = isTokenImport;
        this.dottedAsNames = dottedAsNames;
    }

    public boolean getIsTokenImport() {
        return isTokenImport;
    }

    public DottedAsNames getDottedAsNames() {
        return dottedAsNames;
    }
}
