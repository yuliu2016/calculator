package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * arguments:
 * *   | ','.argument+ [','] [NEWLINE]
 */
public final class Arguments extends NodeWrapper {

    public Arguments(ParseTreeNode node) {
        super(node);
    }

    public List<Argument> arguments() {
        return getList(0, Argument::new);
    }

    public boolean isComma() {
        return is(1);
    }

    public String newline() {
        return get(2, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(2);
    }
}
