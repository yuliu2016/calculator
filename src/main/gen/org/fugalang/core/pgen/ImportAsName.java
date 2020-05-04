package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

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
        addRequired(name());
        addOptional(importAsName2());
    }

    public String name() {
        var element = getItem(0);
        element.failIfAbsent(TokenType.NAME);
        return element.asString();
    }

    public ImportAsName2 importAsName2() {
        var element = getItem(1);
        if (!element.isPresent(ImportAsName2.RULE)) {
            return null;
        }
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

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) ImportAsName2.parse(parseTree, level + 1);

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
            addRequired(isTokenAs(), "as");
            addRequired(name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
