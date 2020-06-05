package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * as_name: 'as' NAME
 */
public final class AsName extends NodeWrapper {

    public AsName(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }
}
