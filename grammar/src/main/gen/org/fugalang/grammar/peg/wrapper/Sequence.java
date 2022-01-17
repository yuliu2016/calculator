package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sequence:
 * *   | primary+ [inline_hint] [result_expr]
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primaries() {
        return getList(0, Primary::new);
    }

    public InlineHint inlineHint() {
        return new InlineHint(get(1));
    }

    public boolean hasInlineHint() {
        return has(1);
    }

    public ResultExpr resultExpr() {
        return new ResultExpr(get(2));
    }

    public boolean hasResultExpr() {
        return has(2);
    }
}
