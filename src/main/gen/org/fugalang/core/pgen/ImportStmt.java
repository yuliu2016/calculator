package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// import_stmt: 'import_name' | 'import_from'
public final class ImportStmt extends DisjunctionRule {
    private final ImportName importName;
    private final ImportFrom importFrom;

    public ImportStmt(
            ImportName importName,
            ImportFrom importFrom
    ) {
        this.importName = importName;
        this.importFrom = importFrom;

        addChoice("importName", importName);
        addChoice("importFrom", importFrom);
    }

    public ImportName getImportName() {
        return importName;
    }

    public ImportFrom getImportFrom() {
        return importFrom;
    }
}
