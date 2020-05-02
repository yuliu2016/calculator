package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_from: 'from' ('.'* 'dotted_name' | '.'+) 'import' ('*' | '(' 'import_as_names' ')' | 'import_as_names')
 */
public final class ImportFrom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_from", RuleType.Conjunction, true);

    public static ImportFrom of(ParseTreeNode node) {
        return new ImportFrom(node);
    }

    private ImportFrom(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenFrom", isTokenFrom());
        addRequired("importFrom2", importFrom2());
        addRequired("isTokenImport", isTokenImport());
        addRequired("importFrom4", importFrom4());
    }

    public boolean isTokenFrom() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public ImportFrom2 importFrom2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return ImportFrom2.of(element);
    }

    public boolean isTokenImport() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public ImportFrom4 importFrom4() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return ImportFrom4.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("from");
        result = result && ImportFrom2.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("import");
        result = result && ImportFrom4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '.'* 'dotted_name' | '.'+
     */
    public static final class ImportFrom2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_from:2", RuleType.Disjunction, false);

        public static ImportFrom2 of(ParseTreeNode node) {
            return new ImportFrom2(node);
        }

        private ImportFrom2(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<Boolean> isTokenDotList;

        @Override
        protected void buildRule() {
            addChoice("importFrom21", importFrom21());
            addChoice("isTokenDotList", isTokenDotList());
        }

        public ImportFrom21 importFrom21() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return ImportFrom21.of(element);
        }

        public boolean hasImportFrom21() {
            return importFrom21() != null;
        }

        public List<Boolean> isTokenDotList() {
            return isTokenDotList;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = ImportFrom21.parse(parseTree, level + 1);
            parseTree.enterCollection();
            result = result || parseTree.consumeTokenLiteral(".");
            while (true) {
                var pos = parseTree.position();
                if (!parseTree.consumeTokenLiteral(".") ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '.'* 'dotted_name'
     */
    public static final class ImportFrom21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_from:2:1", RuleType.Conjunction, false);

        public static ImportFrom21 of(ParseTreeNode node) {
            return new ImportFrom21(node);
        }

        private ImportFrom21(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<Boolean> isTokenDotList;

        @Override
        protected void buildRule() {
            addRequired("isTokenDotList", isTokenDotList());
            addRequired("dottedName", dottedName());
        }

        public List<Boolean> isTokenDotList() {
            return isTokenDotList;
        }

        public DottedName dottedName() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return DottedName.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!parseTree.consumeTokenLiteral(".") ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = DottedName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '*' | '(' 'import_as_names' ')' | 'import_as_names'
     */
    public static final class ImportFrom4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_from:4", RuleType.Disjunction, false);

        public static ImportFrom4 of(ParseTreeNode node) {
            return new ImportFrom4(node);
        }

        private ImportFrom4(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenTimes", isTokenTimes());
            addChoice("importFrom42", importFrom42());
            addChoice("importAsNames", importAsNames());
        }

        public boolean isTokenTimes() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ImportFrom42 importFrom42() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ImportFrom42.of(element);
        }

        public boolean hasImportFrom42() {
            return importFrom42() != null;
        }

        public ImportAsNames importAsNames() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return ImportAsNames.of(element);
        }

        public boolean hasImportAsNames() {
            return importAsNames() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("*");
            result = result || ImportFrom42.parse(parseTree, level + 1);
            result = result || ImportAsNames.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '(' 'import_as_names' ')'
     */
    public static final class ImportFrom42 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_from:4:2", RuleType.Conjunction, false);

        public static ImportFrom42 of(ParseTreeNode node) {
            return new ImportFrom42(node);
        }

        private ImportFrom42(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenLpar", isTokenLpar());
            addRequired("importAsNames", importAsNames());
            addRequired("isTokenRpar", isTokenRpar());
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ImportAsNames importAsNames() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ImportAsNames.of(element);
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            result = result && ImportAsNames.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
