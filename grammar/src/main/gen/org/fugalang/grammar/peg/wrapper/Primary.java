package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * primary:
 * *   | delimited
 * *   | '&' item
 * *   | '!' item
 * *   | item '*'
 * *   | item '+'
 * *   | item
 */
public final class Primary extends NodeWrapper {

    public Primary(ParseTreeNode node) {
        super(node);
    }

    public Delimited delimited() {
        return new Delimited(get(0));
    }

    public boolean hasDelimited() {
        return has(0);
    }

    public Primary2 bitAndItem() {
        return new Primary2(get(1));
    }

    public boolean hasBitAndItem() {
        return has(1);
    }

    public Primary3 notItem() {
        return new Primary3(get(2));
    }

    public boolean hasNotItem() {
        return has(2);
    }

    public Primary4 itemTimes() {
        return new Primary4(get(3));
    }

    public boolean hasItemTimes() {
        return has(3);
    }

    public Primary5 itemPlus() {
        return new Primary5(get(4));
    }

    public boolean hasItemPlus() {
        return has(4);
    }

    public Item item() {
        return new Item(get(5));
    }

    public boolean hasItem() {
        return has(5);
    }

    /**
     * '&' item
     */
    public static final class Primary2 extends NodeWrapper {

        public Primary2(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(1));
        }
    }

    /**
     * '!' item
     */
    public static final class Primary3 extends NodeWrapper {

        public Primary3(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(1));
        }
    }

    /**
     * item '*'
     */
    public static final class Primary4 extends NodeWrapper {

        public Primary4(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(0));
        }
    }

    /**
     * item '+'
     */
    public static final class Primary5 extends NodeWrapper {

        public Primary5(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(0));
        }
    }
}
