package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * import_as_name: NAME [as_name]
 */
public final class ImportAsName extends NodeWrapper {

    public ImportAsName(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public AsName asName() {
        return new AsName(get(1));
    }

    public boolean hasAsName() {
        return has(1);
    }
}
