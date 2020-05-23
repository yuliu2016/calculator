package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * import_as_name: 'NAME' ['as' 'NAME']
 */
public final class ImportAsName extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("import_as_name", RuleType.Conjunction);

    public static ImportAsName of(ParseTreeNode node) {
        return new ImportAsName(node);
    }

    private ImportAsName(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public ImportAsName2 asName() {
        return get(1, ImportAsName2::of);
    }

    public boolean hasAsName() {
        return has(1, ImportAsName2.RULE);
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ImportAsName2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("import_as_name:2", RuleType.Conjunction);

        public static ImportAsName2 of(ParseTreeNode node) {
            return new ImportAsName2(node);
        }

        private ImportAsName2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
