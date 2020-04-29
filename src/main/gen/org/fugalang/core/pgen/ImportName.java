package org.fugalang.core.pgen;

// import_name: 'import' 'dotted_as_names'
public class ImportName {
    public final boolean isTokenImport;
    public final DottedAsNames dottedAsNames;

    public ImportName(
            boolean isTokenImport,
            DottedAsNames dottedAsNames
    ) {
        this.isTokenImport = isTokenImport;
        this.dottedAsNames = dottedAsNames;
    }
}
