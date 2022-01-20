package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * directive:
 * *   | '.' NAME '(' [','.argument+] ')' NEWLINE
 */
public final class Directive extends NodeWrapper {

    public Directive(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public List<Argument> arguments() {
        return getList(3, Argument::new);
    }

    public String newline() {
        return get(5, TokenType.NEWLINE);
    }
}
