package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
 */
public final class DictItem extends NodeWrapper {

    public DictItem(ParseTreeNode node) {
        super(ParserRules.DICT_ITEM, node);
    }

    public DictItem1 exprExpr() {
        return get(0, DictItem1::new);
    }

    public boolean hasExprExpr() {
        return has(0, ParserRules.DICT_ITEM_1);
    }

    public DictItem2 bitwiseOr() {
        return get(1, DictItem2::new);
    }

    public boolean hasBitwiseOr() {
        return has(1, ParserRules.DICT_ITEM_2);
    }

    /**
     * 'expr' ':' 'expr'
     */
    public static final class DictItem1 extends NodeWrapper {

        public DictItem1(ParseTreeNode node) {
            super(ParserRules.DICT_ITEM_1, node);
        }

        public Expr expr() {
            return get(0, Expr::new);
        }

        public Expr expr1() {
            return get(2, Expr::new);
        }
    }

    /**
     * '**' 'bitwise_or'
     */
    public static final class DictItem2 extends NodeWrapper {

        public DictItem2(ParseTreeNode node) {
            super(ParserRules.DICT_ITEM_2, node);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr::new);
        }
    }
}
