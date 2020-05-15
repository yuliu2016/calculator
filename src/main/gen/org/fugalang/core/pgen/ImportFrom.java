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
        return ImportFromNames.of(get(1));
    }

    public ImportFrom4 importFrom4() {
        return ImportFrom4.of(get(3));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("from");
        r = r && ImportFromNames.parse(t, lv + 1);
        r = r && t.consume("import");
        r = r && ImportFrom4.parse(t, lv + 1);
        t.exit(r);
        return r;
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
            return ImportFrom42.of(get(1));
        }

        public boolean hasImportFrom42() {
            return has(1, ImportFrom42.RULE);
        }

        public ImportAsNames importAsNames() {
            return ImportAsNames.of(get(2));
        }

        public boolean hasImportAsNames() {
            return has(2, ImportAsNames.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("*");
            r = r || ImportFrom42.parse(t, lv + 1);
            r = r || ImportAsNames.parse(t, lv + 1);
            t.exit(r);
            return r;
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
            return ImportAsNames.of(get(1));
        }

        public boolean isComma() {
            return is(2);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("(");
            r = r && ImportAsNames.parse(t, lv + 1);
            if (r) t.consume(",");
            r = r && t.consume(")");
            t.exit(r);
            return r;
        }
    }
}
