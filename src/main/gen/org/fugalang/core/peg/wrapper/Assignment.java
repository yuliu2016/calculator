package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * assignment:
 * *   | pubassign
 * *   | annassign
 * *   | augassign
 * *   | simple_assign
 */
public final class Assignment extends NodeWrapper {

    public Assignment(ParseTreeNode node) {
        super(node);
    }

    public Pubassign pubassign() {
        return new Pubassign(get(0));
    }

    public boolean hasPubassign() {
        return has(0);
    }

    public Annassign annassign() {
        return new Annassign(get(1));
    }

    public boolean hasAnnassign() {
        return has(1);
    }

    public Augassign augassign() {
        return new Augassign(get(2));
    }

    public boolean hasAugassign() {
        return has(2);
    }

    public SimpleAssign simpleAssign() {
        return new SimpleAssign(get(3));
    }

    public boolean hasSimpleAssign() {
        return has(3);
    }
}
