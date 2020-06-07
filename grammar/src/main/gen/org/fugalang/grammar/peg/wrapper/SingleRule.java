package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * single_rule:
 * *   | NAME ':' [NEWLINE '|'] or_rule NEWLINE
 */
public final class SingleRule extends NodeWrapper {

    public SingleRule(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SingleRule3 newlineBitOr() {
        return new SingleRule3(get(2));
    }

    public boolean hasNewlineBitOr() {
        return has(2);
    }

    public OrRule orRule() {
        return new OrRule(get(3));
    }

    public String newline() {
        return get(4, TokenType.NEWLINE);
    }

    /**
     * NEWLINE '|'
     */
    public static final class SingleRule3 extends NodeWrapper {

        public SingleRule3(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }
    }
}
