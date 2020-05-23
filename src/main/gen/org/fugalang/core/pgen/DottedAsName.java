package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * dotted_as_name: dotted_name ['as' NAME]
 */
public final class DottedAsName extends NodeWrapper {

    public DottedAsName(ParseTreeNode node) {
        super(node);
    }

    public DottedName dottedName() {
        return get(0, DottedName.class);
    }

    public DottedAsName2 asName() {
        return get(1, DottedAsName2.class);
    }

    public boolean hasAsName() {
        return has(1);
    }

    /**
     * 'as' NAME
     */
    public static final class DottedAsName2 extends NodeWrapper {

        public DottedAsName2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
