package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * builder_hint:
 * *   | '<' name_list '>'
 */
public final class BuilderHint extends NodeWrapper {

    public BuilderHint(ParseTreeNode node) {
        super(node);
    }

    public NameList nameList() {
        return new NameList(get(1));
    }
}
