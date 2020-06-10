package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * alt_list:
 * *   | sequence ([NEWLINE] '|' sequence)*
 */
public final class AltList extends NodeWrapper {

    public AltList(ParseTreeNode node) {
        super(node);
    }

    public Sequence sequence() {
        return new Sequence(get(0));
    }

    public List<AltList2> altList2s() {
        return getList(1, AltList2::new);
    }

    /**
     * [NEWLINE] '|' sequence
     */
    public static final class AltList2 extends NodeWrapper {

        public AltList2(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0);
        }

        public Sequence sequence() {
            return new Sequence(get(2));
        }
    }
}
