package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * finally_suite: 'finally' 'suite'
 */
public final class FinallySuite extends NodeWrapper {

    public FinallySuite(ParseTreeNode node) {
        super(node);
    }

    public Suite suite() {
        return get(1, Suite.class);
    }
}
