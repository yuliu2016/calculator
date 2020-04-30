package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// import_from: 'from' ('.'* 'dotted_name' | '.'+) 'import' ('*' | '(' 'import_as_names' ')' | 'import_as_names')
public final class ImportFrom extends ConjunctionRule {
    public static final String RULE_NAME = "import_from";

    private final boolean isTokenFrom;
    private final ImportFrom2 importFrom2;
    private final boolean isTokenImport;
    private final ImportFrom4 importFrom4;

    public ImportFrom(
            boolean isTokenFrom,
            ImportFrom2 importFrom2,
            boolean isTokenImport,
            ImportFrom4 importFrom4
    ) {
        this.isTokenFrom = isTokenFrom;
        this.importFrom2 = importFrom2;
        this.isTokenImport = isTokenImport;
        this.importFrom4 = importFrom4;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenFrom", isTokenFrom);
        addRequired("importFrom2", importFrom2);
        addRequired("isTokenImport", isTokenImport);
        addRequired("importFrom4", importFrom4);
    }

    public boolean isTokenFrom() {
        return isTokenFrom;
    }

    public ImportFrom2 importFrom2() {
        return importFrom2;
    }

    public boolean isTokenImport() {
        return isTokenImport;
    }

    public ImportFrom4 importFrom4() {
        return importFrom4;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // '.'* 'dotted_name' | '.'+
    public static final class ImportFrom2 extends DisjunctionRule {
        public static final String RULE_NAME = "import_from:2";

        private final ImportFrom21 importFrom21;
        private final boolean isTokenDot;
        private final List<Boolean> isTokenDotList;

        public ImportFrom2(
                ImportFrom21 importFrom21,
                boolean isTokenDot,
                List<Boolean> isTokenDotList
        ) {
            this.importFrom21 = importFrom21;
            this.isTokenDot = isTokenDot;
            this.isTokenDotList = isTokenDotList;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("importFrom21", importFrom21);
            addChoice("isTokenDot", isTokenDot);
            addChoice("isTokenDotList", isTokenDotList);
        }

        public ImportFrom21 importFrom21() {
            return importFrom21;
        }

        public boolean isTokenDot() {
            return isTokenDot;
        }

        public List<Boolean> isTokenDotList() {
            return isTokenDotList;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '.'* 'dotted_name'
    public static final class ImportFrom21 extends ConjunctionRule {
        public static final String RULE_NAME = "import_from:2:1";

        private final List<Boolean> isTokenDotList;
        private final DottedName dottedName;

        public ImportFrom21(
                List<Boolean> isTokenDotList,
                DottedName dottedName
        ) {
            this.isTokenDotList = isTokenDotList;
            this.dottedName = dottedName;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenDotList", isTokenDotList);
            addRequired("dottedName", dottedName);
        }

        public List<Boolean> isTokenDotList() {
            return isTokenDotList;
        }

        public DottedName dottedName() {
            return dottedName;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '*' | '(' 'import_as_names' ')' | 'import_as_names'
    public static final class ImportFrom4 extends DisjunctionRule {
        public static final String RULE_NAME = "import_from:4";

        private final boolean isTokenTimes;
        private final ImportFrom42 importFrom42;
        private final ImportAsNames importAsNames;

        public ImportFrom4(
                boolean isTokenTimes,
                ImportFrom42 importFrom42,
                ImportAsNames importAsNames
        ) {
            this.isTokenTimes = isTokenTimes;
            this.importFrom42 = importFrom42;
            this.importAsNames = importAsNames;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("isTokenTimes", isTokenTimes);
            addChoice("importFrom42", importFrom42);
            addChoice("importAsNames", importAsNames);
        }

        public boolean isTokenTimes() {
            return isTokenTimes;
        }

        public ImportFrom42 importFrom42() {
            return importFrom42;
        }

        public ImportAsNames importAsNames() {
            return importAsNames;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '(' 'import_as_names' ')'
    public static final class ImportFrom42 extends ConjunctionRule {
        public static final String RULE_NAME = "import_from:4:2";

        private final boolean isTokenLpar;
        private final ImportAsNames importAsNames;
        private final boolean isTokenRpar;

        public ImportFrom42(
                boolean isTokenLpar,
                ImportAsNames importAsNames,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.importAsNames = importAsNames;
            this.isTokenRpar = isTokenRpar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenLpar", isTokenLpar);
            addRequired("importAsNames", importAsNames);
            addRequired("isTokenRpar", isTokenRpar);
        }

        public boolean isTokenLpar() {
            return isTokenLpar;
        }

        public ImportAsNames importAsNames() {
            return importAsNames;
        }

        public boolean isTokenRpar() {
            return isTokenRpar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
