package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sequence:
 * *   | primary+ [result_expr]
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primaries() {
        return getList(0, Primary::new);
    }

    public ResultExpr resultExpr() {
        return new ResultExpr(get(1));
    }

    public boolean hasResultExpr() {
        return has(1);
    }
}
