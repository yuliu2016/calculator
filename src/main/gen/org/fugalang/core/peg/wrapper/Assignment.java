package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * assignment:
 * *   | expassign
 * *   | annassign
 * *   | augassign
 * *   | '='.exprlist_star+
 */
public final class Assignment extends NodeWrapper {

    public Assignment(ParseTreeNode node) {
        super(node);
    }

    public Expassign expassign() {
        return new Expassign(get(0));
    }

    public boolean hasExpassign() {
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

    public List<ExprlistStar> exprlistStars() {
        return getList(3, ExprlistStar::new);
    }
}
