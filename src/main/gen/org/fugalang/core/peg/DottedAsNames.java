package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * dotted_as_names: ','.dotted_as_name+
 */
public final class DottedAsNames extends NodeWrapper {

    public DottedAsNames(ParseTreeNode node) {
        super(node);
    }

    public List<DottedAsName> dottedAsNames() {
        return getList(0, DottedAsName::new);
    }
}
