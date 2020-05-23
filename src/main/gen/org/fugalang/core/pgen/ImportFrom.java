package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_from: 'from' 'import_from_names' 'import' ('*' | '(' 'import_as_names' [','] ')' | 'import_as_names')
 */
public final class ImportFrom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_from", RuleType.Conjunction);

    public static ImportFrom of(ParseTreeNode node) {
        return new ImportFrom(node);
    }

    private ImportFrom(ParseTreeNode node) {
        super(RULE, node);
    }

    public ImportFromNames importFromNames() {
        return get(1, ImportFromNames::of);
    }

    public ImportFrom4 importFrom4() {
        return get(3, ImportFrom4::of);
    }

    /**
     * '*' | '(' 'import_as_names' [','] ')' | 'import_as_names'
     */
    public static final class ImportFrom4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_from:4", RuleType.Disjunction);

        public static ImportFrom4 of(ParseTreeNode node) {
            return new ImportFrom4(node);
        }

        private ImportFrom4(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public ImportFrom42 importFrom42() {
            return get(1, ImportFrom42::of);
        }

        public boolean hasImportFrom42() {
            return has(1, ImportFrom42.RULE);
        }

        public ImportAsNames importAsNames() {
            return get(2, ImportAsNames::of);
        }

        public boolean hasImportAsNames() {
            return has(2, ImportAsNames.RULE);
        }
    }

    /**
     * '(' 'import_as_names' [','] ')'
     */
    public static final class ImportFrom42 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_from:4:2", RuleType.Conjunction);

        public static ImportFrom42 of(ParseTreeNode node) {
            return new ImportFrom42(node);
        }

        private ImportFrom42(ParseTreeNode node) {
            super(RULE, node);
        }

        public ImportAsNames importAsNames() {
            return get(1, ImportAsNames::of);
        }

        public boolean isComma() {
            return is(2);
        }
    }
}
