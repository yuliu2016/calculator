package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * directive:
 * *   | '.' NAME '(' [arguments] ')' NEWLINE
 */
public final class Directive extends NodeWrapper {

    public Directive(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public Arguments arguments() {
        return new Arguments(get(3));
    }

    public boolean hasArguments() {
        return has(3);
    }

    public String newline() {
        return get(5, TokenType.NEWLINE);
    }
}
