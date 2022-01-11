package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * sequence:
 * *   | primary+ [[NEWLINE] '{' result_expr '}']
 */
public final class Sequence extends NodeWrapper {

    public Sequence(ParseTreeNode node) {
        super(node);
    }

    public List<Primary> primaries() {
        return getList(0, Primary::new);
    }

    public Sequence2 sequence2() {
        return new Sequence2(get(1));
    }

    public boolean hasSequence2() {
        return has(1);
    }

    /**
     * [NEWLINE] '{' result_expr '}'
     */
    public static final class Sequence2 extends NodeWrapper {

        public Sequence2(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0);
        }

        public ResultExpr resultExpr() {
            return new ResultExpr(get(2));
        }
    }
}
