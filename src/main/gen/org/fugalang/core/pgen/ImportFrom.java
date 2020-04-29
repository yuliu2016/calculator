package org.fugalang.core.pgen;

// import_from: 'from' (('.')* 'dotted_name' | ('.')+) 'import' ('*' | '(' 'import_as_names' ')' | 'import_as_names')
public class ImportFrom {
    public final boolean isTokenFrom;
    public final ImportFrom2Group importFrom2Group;
    public final boolean isTokenImport;
    public final ImportFrom4Group importFrom4Group;

    public ImportFrom(
            boolean isTokenFrom,
            ImportFrom2Group importFrom2Group,
            boolean isTokenImport,
            ImportFrom4Group importFrom4Group
    ) {
        this.isTokenFrom = isTokenFrom;
        this.importFrom2Group = importFrom2Group;
        this.isTokenImport = isTokenImport;
        this.importFrom4Group = importFrom4Group;
    }
}
