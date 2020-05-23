package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * parameters: '(' ['arglist'] ')'
 */
public final class Parameters extends NodeWrapper {

    public Parameters(ParseTreeNode node) {
        super(ParserRules.PARAMETERS, node);
    }

    public Arglist arglist() {
        return get(1, Arglist.class);
    }

    public boolean hasArglist() {
        return has(1);
    }
}
