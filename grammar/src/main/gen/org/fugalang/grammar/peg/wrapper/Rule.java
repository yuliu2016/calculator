package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * rule:
 * *   | NAME ':' NEWLINE '|' alt_list NEWLINE
 */
public final class Rule extends NodeWrapper {

    public Rule(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public String newline() {
        return get(2, TokenType.NEWLINE);
    }

    public AltList altList() {
        return new AltList(get(4));
    }

    public String newline1() {
        return get(5, TokenType.NEWLINE);
    }
}
