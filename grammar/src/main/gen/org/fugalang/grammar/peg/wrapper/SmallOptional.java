package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * small_optional:
 * *   | (NAME | STRING) '?'
 */
public final class SmallOptional extends NodeWrapper {

    public SmallOptional(ParseTreeNode node) {
        super(node);
    }

    public SmallOptional1 nameOrString() {
        return new SmallOptional1(get(0));
    }

    /**
     * NAME | STRING
     */
    public static final class SmallOptional1 extends NodeWrapper {

        public SmallOptional1(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public boolean hasName() {
            return has(0);
        }

        public String string() {
            return get(1, TokenType.STRING);
        }

        public boolean hasString() {
            return has(1);
        }
    }
}
