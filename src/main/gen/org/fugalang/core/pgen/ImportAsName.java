package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_as_name: 'NAME' ['as' 'NAME']
 */
public final class ImportAsName extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("import_as_name", RuleType.Conjunction, true);

    private final String name;
    private final ImportAsName2 importAsName2;

    public ImportAsName(
            String name,
            ImportAsName2 importAsName2
    ) {
        this.name = name;
        this.importAsName2 = importAsName2;
    }

    @Override
    protected void buildRule() {
        addRequired("name", name());
        addOptional("importAsName2", importAsName2());
    }

    public String name() {
        return name;
    }

    public ImportAsName2 importAsName2() {
        return importAsName2;
    }

    public boolean hasImportAsName2() {
        return importAsName2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        ImportAsName2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ImportAsName2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("import_as_name:2", RuleType.Conjunction, false);

        private final boolean isTokenAs;
        private final String name;

        public ImportAsName2(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
