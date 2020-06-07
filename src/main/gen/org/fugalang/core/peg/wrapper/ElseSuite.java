package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * else_suite:
 * *   | 'else' suite
 */
public final class ElseSuite extends NodeWrapper {

    public ElseSuite(ParseTreeNode node) {
        super(node);
    }

    public Suite suite() {
        return new Suite(get(1));
    }
}
