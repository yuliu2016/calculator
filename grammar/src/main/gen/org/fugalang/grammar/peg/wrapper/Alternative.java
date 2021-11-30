package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * alternative:
 * *   | [NEWLINE] '|' sequence
 */
public final class Alternative extends NodeWrapper {

    public Alternative(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public Sequence sequence() {
        return new Sequence(get(2));
    }
}
