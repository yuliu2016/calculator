package org.fugalang.core.pgen;

// import_stmt: 'import_name' | 'import_from'
public class ImportStmt {
    private final ImportName importName;
    private final ImportFrom importFrom;

    public ImportStmt(
            ImportName importName,
            ImportFrom importFrom
    ) {
        this.importName = importName;
        this.importFrom = importFrom;
    }

    public ImportName getImportName() {
        return importName;
    }

    public ImportFrom getImportFrom() {
        return importFrom;
    }
}
