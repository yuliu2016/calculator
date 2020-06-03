package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_or_set: '{' [dict_maker | exprlist_star] '}'
 */
public final class DictOrSet extends NodeWrapper {

    public DictOrSet(ParseTreeNode node) {
        super(node);
    }

    public DictOrSet2 dictMakerOrExprlistStar() {
        return new DictOrSet2(get(1));
    }

    public boolean hasDictMakerOrExprlistStar() {
        return has(1);
    }

    /**
     * dict_maker | exprlist_star
     */
    public static final class DictOrSet2 extends NodeWrapper {

        public DictOrSet2(ParseTreeNode node) {
            super(node);
        }

        public DictMaker dictMaker() {
            return new DictMaker(get(0));
        }

        public boolean hasDictMaker() {
            return has(0);
        }

        public ExprlistStar exprlistStar() {
            return new ExprlistStar(get(1));
        }

        public boolean hasExprlistStar() {
            return has(1);
        }
    }
}
