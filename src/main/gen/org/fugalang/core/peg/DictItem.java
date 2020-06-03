package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_item: expr ':' expr | '**' bitwise_or
 */
public final class DictItem extends NodeWrapper {

    public DictItem(ParseTreeNode node) {
        super(node);
    }

    public DictItem1 exprColonExpr() {
        return new DictItem1(get(0));
    }

    public boolean hasExprColonExpr() {
        return has(0);
    }

    public DictItem2 powerBitwiseOr() {
        return new DictItem2(get(1));
    }

    public boolean hasPowerBitwiseOr() {
        return has(1);
    }

    /**
     * expr ':' expr
     */
    public static final class DictItem1 extends NodeWrapper {

        public DictItem1(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(0));
        }

        public Expr expr1() {
            return new Expr(get(2));
        }
    }

    /**
     * '**' bitwise_or
     */
    public static final class DictItem2 extends NodeWrapper {

        public DictItem2(ParseTreeNode node) {
            super(node);
        }

        public BitwiseOr bitwiseOr() {
            return new BitwiseOr(get(1));
        }
    }
}
