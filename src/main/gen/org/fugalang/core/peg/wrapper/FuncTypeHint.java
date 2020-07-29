package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * func_type_hint:
 * *   | '<' name_list '>'
 */
public final class FuncTypeHint extends NodeWrapper {

    public FuncTypeHint(ParseTreeNode node) {
        super(node);
    }

    public NameList nameList() {
        return new NameList(get(1));
    }
}
