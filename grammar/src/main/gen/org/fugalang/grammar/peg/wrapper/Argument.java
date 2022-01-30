package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * argument:
 * *   | STRING
 * *   | [NEWLINE] NAME '=' STRING
 */
public final class Argument extends NodeWrapper {

    public Argument(ParseTreeNode node) {
        super(node);
    }

    public String string() {
        return get(0, TokenType.STRING);
    }

    public boolean hasString() {
        return has(0);
    }

    public Argument2 argument2() {
        return new Argument2(get(1));
    }

    public boolean hasArgument2() {
        return has(1);
    }

    /**
     * [NEWLINE] NAME '=' STRING
     */
    public static final class Argument2 extends NodeWrapper {

        public Argument2(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }

        public String string() {
            return get(3, TokenType.STRING);
        }
    }
}
