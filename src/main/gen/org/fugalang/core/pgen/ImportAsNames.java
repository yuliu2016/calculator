package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_as_names: 'import_as_name' (',' 'import_as_name')*
 */
public final class ImportAsNames extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_as_names", RuleType.Conjunction);

    public static ImportAsNames of(ParseTreeNode node) {
        return new ImportAsNames(node);
    }

    private ImportAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public ImportAsName importAsName() {
        return ImportAsName.of(getItem(0));
    }

    public List<ImportAsNames2> importAsNames2List() {
        return getList(1, ImportAsNames2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = ImportAsName.parse(t, lv + 1);
        if (r) parseImportAsNames2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseImportAsNames2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ImportAsNames2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'import_as_name'
     */
    public static final class ImportAsNames2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_as_names:2", RuleType.Conjunction);

        public static ImportAsNames2 of(ParseTreeNode node) {
            return new ImportAsNames2(node);
        }

        private ImportAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ImportAsName importAsName() {
            return ImportAsName.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && ImportAsName.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
