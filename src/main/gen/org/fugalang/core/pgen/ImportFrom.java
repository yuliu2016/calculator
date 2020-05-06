package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_from: 'from' 'import_from_names' 'import' ('*' | '(' 'import_as_names' [','] ')' | 'import_as_names')
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
        addRequired(isTokenFrom(), "from");
        addRequired(importFromNames());
        addRequired(isTokenImport(), "import");
        addRequired(importFrom4());
    }

    public boolean isTokenFrom() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public ImportFromNames importFromNames() {
        var element = getItem(1);
        element.failIfAbsent(ImportFromNames.RULE);
        return ImportFromNames.of(element);
    }

    public boolean isTokenImport() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public ImportFrom4 importFrom4() {
        var element = getItem(3);
        element.failIfAbsent(ImportFrom4.RULE);
        return ImportFrom4.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("from");
        result = result && ImportFromNames.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("import");
        result = result && ImportFrom4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '*' | '(' 'import_as_names' [','] ')' | 'import_as_names'
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
            addChoice(isTokenTimes(), "*");
            addChoice(importFrom42OrNull());
            addChoice(importAsNamesOrNull());
        }

        public boolean isTokenTimes() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ImportFrom42 importFrom42() {
            var element = getItem(1);
            element.failIfAbsent(ImportFrom42.RULE);
            return ImportFrom42.of(element);
        }

        public ImportFrom42 importFrom42OrNull() {
            var element = getItem(1);
            if (!element.isPresent(ImportFrom42.RULE)) {
                return null;
            }
            return ImportFrom42.of(element);
        }

        public boolean hasImportFrom42() {
            var element = getItem(1);
            return element.isPresent(ImportFrom42.RULE);
        }

        public ImportAsNames importAsNames() {
            var element = getItem(2);
            element.failIfAbsent(ImportAsNames.RULE);
            return ImportAsNames.of(element);
        }

        public ImportAsNames importAsNamesOrNull() {
            var element = getItem(2);
            if (!element.isPresent(ImportAsNames.RULE)) {
                return null;
            }
            return ImportAsNames.of(element);
        }

        public boolean hasImportAsNames() {
            var element = getItem(2);
            return element.isPresent(ImportAsNames.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("*");
            result = result || ImportFrom42.parse(parseTree, level + 1);
            result = result || ImportAsNames.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '(' 'import_as_names' [','] ')'
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
            addRequired(isTokenLpar(), "(");
            addRequired(importAsNames());
            addOptional(isTokenComma(), ",");
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ImportAsNames importAsNames() {
            var element = getItem(1);
            element.failIfAbsent(ImportAsNames.RULE);
            return ImportAsNames.of(element);
        }

        public boolean isTokenComma() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public boolean isTokenRpar() {
            var element = getItem(3);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            result = result && ImportAsNames.parse(parseTree, level + 1);
            if (result) parseTree.consumeToken(",");
            result = result && parseTree.consumeToken(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
