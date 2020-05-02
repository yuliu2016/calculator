package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * import_as_name: 'NAME' ['as' 'NAME']
 */
public final class ImportAsName extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("import_as_name", RuleType.Conjunction, true);

    public static ImportAsName of(ParseTreeNode node) {
        return new ImportAsName(node);
    }

    private ImportAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("name", name());
        addOptional("importAsName2", importAsName2());
    }

    public String name() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return (String) element.asObject();
    }

    public ImportAsName2 importAsName2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return ImportAsName2.of(element);
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
    public static final class ImportAsName2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("import_as_name:2", RuleType.Conjunction, false);

        public static ImportAsName2 of(ParseTreeNode node) {
            return new ImportAsName2(node);
        }

        private ImportAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return (String) element.asObject();
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
