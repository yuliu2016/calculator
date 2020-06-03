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
        return new Delimited(get(0));
    }

    public boolean hasDelimited() {
        return has(0);
    }

    public Repeat2 itemTimes() {
        return new Repeat2(get(1));
    }

    public boolean hasItemTimes() {
        return has(1);
    }

    public Repeat3 itemPlus() {
        return new Repeat3(get(2));
    }

    public boolean hasItemPlus() {
        return has(2);
    }

    public Item item() {
        return new Item(get(3));
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
            return new Item(get(0));
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
            return new Item(get(0));
        }
    }
}
