package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * rule_suite:
 * *   | ':' NEWLINE '|' alt_list NEWLINE
 */
public final class RuleSuite extends NodeWrapper {

    public RuleSuite(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(1, TokenType.NEWLINE);
    }

    public AltList altList() {
        return new AltList(get(3));
    }

    public String newline1() {
        return get(4, TokenType.NEWLINE);
    }
}
