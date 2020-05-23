package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * subscript: '[' 'slicelist' ']'
 */
public final class Subscript extends NodeWrapper {

    public Subscript(ParseTreeNode node) {
        super(ParserRules.SUBSCRIPT, node);
    }

    public Slicelist slicelist() {
        return get(1, Slicelist::new);
    }
}
