package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * custom_match:
 * *   | '@' expression
 */
public final class CustomMatch extends NodeWrapper {

    public CustomMatch(ParseTreeNode node) {
        super(node);
    }

    public Expression expression() {
        return new Expression(get(1));
    }
}
