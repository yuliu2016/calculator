package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
 */
public final class DictItem extends NodeWrapper {

    public DictItem(ParseTreeNode node) {
        super(node);
    }

    public DictItem1 exprExpr() {
        return get(0, DictItem1.class);
    }

    public boolean hasExprExpr() {
        return has(0);
    }

    public DictItem2 bitwiseOr() {
        return get(1, DictItem2.class);
    }

    public boolean hasBitwiseOr() {
        return has(1);
    }

    /**
     * 'expr' ':' 'expr'
     */
    public static final class DictItem1 extends NodeWrapper {

        public DictItem1(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(0, Expr.class);
        }

        public Expr expr1() {
            return get(2, Expr.class);
        }
    }

    /**
     * '**' 'bitwise_or'
     */
    public static final class DictItem2 extends NodeWrapper {

        public DictItem2(ParseTreeNode node) {
            super(node);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr.class);
        }
    }
}
