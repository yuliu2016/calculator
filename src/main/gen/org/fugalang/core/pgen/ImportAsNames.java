package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_as_names: 'import_as_name' (',' 'import_as_name')*
 */
public final class ImportAsNames extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_as_names", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = ImportAsName.parse(parseTree, level + 1);
        if (result) parseImportAsNames2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseImportAsNames2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ImportAsNames2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'import_as_name'
     */
    public static final class ImportAsNames2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_as_names:2", RuleType.Conjunction, false);

        public static ImportAsNames2 of(ParseTreeNode node) {
            return new ImportAsNames2(node);
        }

        private ImportAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ImportAsName importAsName() {
            return ImportAsName.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && ImportAsName.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
