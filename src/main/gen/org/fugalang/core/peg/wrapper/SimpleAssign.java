package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * simple_assign:
 * *   | (targetlist '=')* exprlist_star
 */
public final class SimpleAssign extends NodeWrapper {

    public SimpleAssign(ParseTreeNode node) {
        super(node);
    }

    public List<SimpleAssign1> targetlistAssigns() {
        return getList(0, SimpleAssign1::new);
    }

    public ExprlistStar exprlistStar() {
        return new ExprlistStar(get(1));
    }

    /**
     * targetlist '='
     */
    public static final class SimpleAssign1 extends NodeWrapper {

        public SimpleAssign1(ParseTreeNode node) {
            super(node);
        }

        public Targetlist targetlist() {
            return new Targetlist(get(0));
        }
    }
}
