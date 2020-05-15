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
        return ImportFromNames.of(getItem(1));
    }

    public ImportFrom4 importFrom4() {
        return ImportFrom4.of(getItem(3));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("from");
        r = r && ImportFromNames.parse(t, lv + 1);
        r = r && t.consumeToken("import");
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

        public boolean isTokenTimes() {
            return getBoolean(0);
        }

        public ImportFrom42 importFrom42() {
            return ImportFrom42.of(getItem(1));
        }

        public boolean hasImportFrom42() {
            return hasItemOfRule(1, ImportFrom42.RULE);
        }

        public ImportAsNames importAsNames() {
            return ImportAsNames.of(getItem(2));
        }

        public boolean hasImportAsNames() {
            return hasItemOfRule(2, ImportAsNames.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("*");
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
            return ImportAsNames.of(getItem(1));
        }

        public boolean isTokenComma() {
            return getBoolean(2);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("(");
            r = r && ImportAsNames.parse(t, lv + 1);
            if (r) t.consumeToken(",");
            r = r && t.consumeToken(")");
            t.exit(r);
            return r;
        }
    }
}
