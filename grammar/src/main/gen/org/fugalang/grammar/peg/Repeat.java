package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * repeat: delimited | item '*' | item '+' | item
 */
public final class Repeat extends NodeWrapper {

    public Repeat(ParseTreeNode node) {
        super(node);
    }

    public Delimited delimited() {
        return get(0, Delimited.class);
    }

    public boolean hasDelimited() {
        return has(0);
    }

    public Repeat2 itemTimes() {
        return get(1, Repeat2.class);
    }

    public boolean hasItemTimes() {
        return has(1);
    }

    public Repeat3 itemPlus() {
        return get(2, Repeat3.class);
    }

    public boolean hasItemPlus() {
        return has(2);
    }

    public Item item() {
        return get(3, Item.class);
    }

    public boolean hasItem() {
        return has(3);
    }

    /**
     * item '*'
     */
    public static final class Repeat2 extends NodeWrapper {

        public Repeat2(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return get(0, Item.class);
        }
    }

    /**
     * item '+'
     */
    public static final class Repeat3 extends NodeWrapper {

        public Repeat3(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return get(0, Item.class);
        }
    }
}
