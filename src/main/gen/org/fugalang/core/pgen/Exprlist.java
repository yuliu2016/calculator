package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends NodeWrapper {

    public Exprlist(ParseTreeNode node) {
        super(FugaRules.EXPRLIST, node);
    }

    public Expr expr() {
        return get(0, Expr.class);
    }

    public List<Exprlist2> exprs() {
        return getList(1, Exprlist2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends NodeWrapper {

        public Exprlist2(ParseTreeNode node) {
            super(FugaRules.EXPRLIST_2, node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}
