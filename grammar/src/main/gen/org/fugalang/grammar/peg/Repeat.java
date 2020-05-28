package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * repeat: item ['*' | '+']
 */
public final class Repeat extends NodeWrapper {

    public Repeat(ParseTreeNode node) {
        super(node);
    }

    public Item item() {
        return get(0, Item.class);
    }

    public Repeat2 timesOrPlus() {
        return get(1, Repeat2.class);
    }

    public boolean hasTimesOrPlus() {
        return has(1);
    }

    /**
     * '*' | '+'
     */
    public static final class Repeat2 extends NodeWrapper {

        public Repeat2(ParseTreeNode node) {
            super(node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public boolean isPlus() {
            return is(1);
        }
    }
}
