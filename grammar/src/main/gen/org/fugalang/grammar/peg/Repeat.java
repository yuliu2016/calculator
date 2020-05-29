package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * repeat: item '*' | item '+' | item
 */
public final class Repeat extends NodeWrapper {

    public Repeat(ParseTreeNode node) {
        super(node);
    }

    public Repeat1 itemTimes() {
        return get(0, Repeat1.class);
    }

    public boolean hasItemTimes() {
        return has(0);
    }

    public Repeat2 itemPlus() {
        return get(1, Repeat2.class);
    }

    public boolean hasItemPlus() {
        return has(1);
    }

    public Item item() {
        return get(2, Item.class);
    }

    public boolean hasItem() {
        return has(2);
    }

    /**
     * item '*'
     */
    public static final class Repeat1 extends NodeWrapper {

        public Repeat1(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return get(0, Item.class);
        }
    }

    /**
     * item '+'
     */
    public static final class Repeat2 extends NodeWrapper {

        public Repeat2(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return get(0, Item.class);
        }
    }
}
