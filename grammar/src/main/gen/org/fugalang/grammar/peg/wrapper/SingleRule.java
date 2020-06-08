package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * single_rule:
 * *   | NAME ':' NEWLINE '|' or_rule NEWLINE
 */
public final class SingleRule extends NodeWrapper {

    public SingleRule(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public String newline() {
        return get(2, TokenType.NEWLINE);
    }

    public OrRule orRule() {
        return new OrRule(get(4));
    }

    public String newline1() {
        return get(5, TokenType.NEWLINE);
    }
}
