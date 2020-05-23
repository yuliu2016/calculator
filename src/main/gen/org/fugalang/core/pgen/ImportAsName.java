package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * import_as_name: 'NAME' ['as' 'NAME']
 */
public final class ImportAsName extends NodeWrapper {

    public ImportAsName(ParseTreeNode node) {
        super(ParserRules.IMPORT_AS_NAME, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public ImportAsName2 asName() {
        return get(1, ImportAsName2::new);
    }

    public boolean hasAsName() {
        return has(1, ParserRules.IMPORT_AS_NAME_2);
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ImportAsName2 extends NodeWrapper {

        public ImportAsName2(ParseTreeNode node) {
            super(ParserRules.IMPORT_AS_NAME_2, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
