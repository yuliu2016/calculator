package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * import_as_names: 'import_as_name' (',' 'import_as_name')* [',']
 */
public final class ImportAsNames extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("import_as_names", RuleType.Conjunction, true);

    private final ImportAsName importAsName;
    private final List<ImportAsNames2> importAsNames2List;
    private final boolean isTokenComma;

    public ImportAsNames(
            ImportAsName importAsName,
            List<ImportAsNames2> importAsNames2List,
            boolean isTokenComma
    ) {
        this.importAsName = importAsName;
        this.importAsNames2List = importAsNames2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("importAsName", importAsName());
        addRequired("importAsNames2List", importAsNames2List());
        addRequired("isTokenComma", isTokenComma());
    }

    public ImportAsName importAsName() {
        return importAsName;
    }

    public List<ImportAsNames2> importAsNames2List() {
        return importAsNames2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = ImportAsName.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ImportAsNames2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'import_as_name'
     */
    public static final class ImportAsNames2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("import_as_names:2", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final ImportAsName importAsName;

        public ImportAsNames2(
                boolean isTokenComma,
                ImportAsName importAsName
        ) {
            this.isTokenComma = isTokenComma;
            this.importAsName = importAsName;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("importAsName", importAsName());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public ImportAsName importAsName() {
            return importAsName;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && ImportAsName.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
