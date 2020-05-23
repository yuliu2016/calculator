package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * trailer: '.' 'NAME' | 'parameters' | 'subscript'
 */
public final class Trailer extends NodeWrapper {

    public Trailer(ParseTreeNode node) {
        super(ParserRules.TRAILER, node);
    }

    public Trailer1 name() {
        return get(0, Trailer1.class);
    }

    public boolean hasName() {
        return has(0);
    }

    public Parameters parameters() {
        return get(1, Parameters.class);
    }

    public boolean hasParameters() {
        return has(1);
    }

    public Subscript subscript() {
        return get(2, Subscript.class);
    }

    public boolean hasSubscript() {
        return has(2);
    }

    /**
     * '.' 'NAME'
     */
    public static final class Trailer1 extends NodeWrapper {

        public Trailer1(ParseTreeNode node) {
            super(ParserRules.TRAILER_1, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
