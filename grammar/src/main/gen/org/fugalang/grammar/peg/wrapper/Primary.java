package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * primary:
 * *   | delimited
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

    public Primary2 itemTimes() {
        return new Primary2(get(1));
    }

    public boolean hasItemTimes() {
        return has(1);
    }

    public Primary3 itemPlus() {
        return new Primary3(get(2));
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
    public static final class Primary2 extends NodeWrapper {

        public Primary2(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(0));
        }
    }

    /**
     * item '+'
     */
    public static final class Primary3 extends NodeWrapper {

        public Primary3(ParseTreeNode node) {
            super(node);
        }

        public Item item() {
            return new Item(get(0));
        }
    }
}
