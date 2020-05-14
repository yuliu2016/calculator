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

    public String name() {
        return getItemOfType(0, TokenType.NAME);
    }

    public ImportAsName2 importAsName2() {
        return ImportAsName2.of(getItem(1));
    }

    public boolean hasImportAsName2() {
        return hasItemOfRule(1, ImportAsName2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        if (result) ImportAsName2.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public String name() {
            return getItemOfType(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(result);
            return result;
        }
    }
}
