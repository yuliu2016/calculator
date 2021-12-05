package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * sequence:
 * *   | primary+ ['{' result_expr '}']
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primaries() {
        return getList(0, Primary::new);
    }

    public Sequence2 lbraceResultExprRbrace() {
        return new Sequence2(get(1));
    }

    public boolean hasLbraceResultExprRbrace() {
        return has(1);
    }

    /**
     * '{' result_expr '}'
     */
    public static final class Sequence2 extends NodeWrapper {

        public Sequence2(ParseTreeNode node) {
            super(node);
        }

        public ResultExpr resultExpr() {
            return new ResultExpr(get(1));
        }
    }
}
