package org.fugalang.core.pgen;

import java.util.List;

// import_from: 'from' ('.'* 'dotted_name' | '.'+) 'import' ('*' | '(' 'import_as_names' ')' | 'import_as_names')
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

    // '.'* 'dotted_name' | '.'+
    public static class ImportFrom2Group {
        public final ImportFrom2Group1 importFrom2Group1;
        public final boolean isTokenDot;
        public final List<Boolean> isTokenDotList;

        public ImportFrom2Group(
                ImportFrom2Group1 importFrom2Group1,
                boolean isTokenDot,
                List<Boolean> isTokenDotList
        ) {
            this.importFrom2Group1 = importFrom2Group1;
            this.isTokenDot = isTokenDot;
            this.isTokenDotList = isTokenDotList;
        }
    }

    // '.'* 'dotted_name'
    public static class ImportFrom2Group1 {
        public final List<Boolean> isTokenDotList;
        public final DottedName dottedName;

        public ImportFrom2Group1(
                List<Boolean> isTokenDotList,
                DottedName dottedName
        ) {
            this.isTokenDotList = isTokenDotList;
            this.dottedName = dottedName;
        }
    }

    // '*' | '(' 'import_as_names' ')' | 'import_as_names'
    public static class ImportFrom4Group {
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

    // '(' 'import_as_names' ')'
    public static class ImportFrom4Group2 {
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
}
