package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * import_as_name: NAME ['as' NAME]
 */
public final class ImportAsName extends NodeWrapper {

    public ImportAsName(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public ImportAsName2 asName() {
        return get(1, ImportAsName2.class);
    }

    public boolean hasAsName() {
        return has(1);
    }

    /**
     * 'as' NAME
     */
    public static final class ImportAsName2 extends NodeWrapper {

        public ImportAsName2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
