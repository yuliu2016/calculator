package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * dotted_name: 'NAME' ('.' 'NAME')*
 */
public final class DottedName extends NodeWrapper {

    public DottedName(ParseTreeNode node) {
        super(FugaRules.DOTTED_NAME, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public List<DottedName2> names() {
        return getList(1, DottedName2.class);
    }

    /**
     * '.' 'NAME'
     */
    public static final class DottedName2 extends NodeWrapper {

        public DottedName2(ParseTreeNode node) {
            super(FugaRules.DOTTED_NAME_2, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
