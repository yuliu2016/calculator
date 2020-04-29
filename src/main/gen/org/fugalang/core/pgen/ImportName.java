package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// import_name: 'import' 'dotted_as_names'
public final class ImportName extends ConjunctionRule {
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
