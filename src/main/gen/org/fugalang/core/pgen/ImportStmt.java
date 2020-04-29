package org.fugalang.core.pgen;

// import_stmt: 'import_name' | 'import_from'
public class ImportStmt {
    public final ImportName importName;
    public final ImportFrom importFrom;

    public ImportStmt(
            ImportName importName,
            ImportFrom importFrom
    ) {
        this.importName = importName;
        this.importFrom = importFrom;
    }
}
