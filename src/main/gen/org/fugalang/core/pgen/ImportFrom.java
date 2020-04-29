package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// import_from: 'from' ('.'* 'dotted_name' | '.'+) 'import' ('*' | '(' 'import_as_names' ')' | 'import_as_names')
public final class ImportFrom extends ConjunctionRule {
    private final boolean isTokenFrom;
    private final ImportFrom2Group importFrom2Group;
    private final boolean isTokenImport;
    private final ImportFrom4Group importFrom4Group;

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

    public boolean getIsTokenFrom() {
        return isTokenFrom;
    }

    public ImportFrom2Group getImportFrom2Group() {
        return importFrom2Group;
    }

    public boolean getIsTokenImport() {
        return isTokenImport;
    }

    public ImportFrom4Group getImportFrom4Group() {
        return importFrom4Group;
    }

    // '.'* 'dotted_name' | '.'+
    public static final class ImportFrom2Group extends DisjunctionRule {
        private final ImportFrom2Group1 importFrom2Group1;
        private final boolean isTokenDot;
        private final List<Boolean> isTokenDotList;

        public ImportFrom2Group(
                ImportFrom2Group1 importFrom2Group1,
                boolean isTokenDot,
                List<Boolean> isTokenDotList
        ) {
            this.importFrom2Group1 = importFrom2Group1;
            this.isTokenDot = isTokenDot;
            this.isTokenDotList = isTokenDotList;
        }

        public ImportFrom2Group1 getImportFrom2Group1() {
            return importFrom2Group1;
        }

        public boolean getIsTokenDot() {
            return isTokenDot;
        }

        public List<Boolean> getIsTokenDotList() {
            return isTokenDotList;
        }
    }

    // '.'* 'dotted_name'
    public static final class ImportFrom2Group1 extends ConjunctionRule {
        private final List<Boolean> isTokenDotList;
        private final DottedName dottedName;

        public ImportFrom2Group1(
                List<Boolean> isTokenDotList,
                DottedName dottedName
        ) {
            this.isTokenDotList = isTokenDotList;
            this.dottedName = dottedName;
        }

        public List<Boolean> getIsTokenDotList() {
            return isTokenDotList;
        }

        public DottedName getDottedName() {
            return dottedName;
        }
    }

    // '*' | '(' 'import_as_names' ')' | 'import_as_names'
    public static final class ImportFrom4Group extends DisjunctionRule {
        private final boolean isTokenTimes;
        private final ImportFrom4Group2 importFrom4Group2;
        private final ImportAsNames importAsNames;

        public ImportFrom4Group(
                boolean isTokenTimes,
                ImportFrom4Group2 importFrom4Group2,
                ImportAsNames importAsNames
        ) {
            this.isTokenTimes = isTokenTimes;
            this.importFrom4Group2 = importFrom4Group2;
            this.importAsNames = importAsNames;
        }

        public boolean getIsTokenTimes() {
            return isTokenTimes;
        }

        public ImportFrom4Group2 getImportFrom4Group2() {
            return importFrom4Group2;
        }

        public ImportAsNames getImportAsNames() {
            return importAsNames;
        }
    }

    // '(' 'import_as_names' ')'
    public static final class ImportFrom4Group2 extends ConjunctionRule {
        private final boolean isTokenLpar;
        private final ImportAsNames importAsNames;
        private final boolean isTokenRpar;

        public ImportFrom4Group2(
                boolean isTokenLpar,
                ImportAsNames importAsNames,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.importAsNames = importAsNames;
            this.isTokenRpar = isTokenRpar;
        }

        public boolean getIsTokenLpar() {
            return isTokenLpar;
        }

        public ImportAsNames getImportAsNames() {
            return importAsNames;
        }

        public boolean getIsTokenRpar() {
            return isTokenRpar;
        }
    }
}
