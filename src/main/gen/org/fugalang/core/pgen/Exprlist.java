package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends NodeWrapper {

    public Exprlist(ParseTreeNode node) {
        super(ParserRules.EXPRLIST, node);
    }

    public Expr expr() {
        return get(0, Expr::new);
    }

    public List<Exprlist2> exprs() {
        return getList(1, Exprlist2::new);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends NodeWrapper {

        public Exprlist2(ParseTreeNode node) {
            super(ParserRules.EXPRLIST_2, node);
        }

        public Expr expr() {
            return get(1, Expr::new);
        }
    }
}
