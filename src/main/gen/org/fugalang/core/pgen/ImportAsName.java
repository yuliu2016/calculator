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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NAME);
        if (r) ImportAsName2.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("as");
            r = r && t.consumeToken(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
