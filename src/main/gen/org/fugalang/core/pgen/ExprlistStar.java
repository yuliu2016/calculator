package org.fugalang.core.pgen;

import java.util.List;

// exprlist_star: ('expr' | 'star_expr') (',' ('expr' | 'star_expr'))* [',']
public class ExprlistStar {
    public final ExprlistStar1Group exprlistStar1Group;
    public final List<ExprlistStar2Group> exprlistStar2GroupList;
    public final boolean isTokenComma;

    public ExprlistStar(
            ExprlistStar1Group exprlistStar1Group,
            List<ExprlistStar2Group> exprlistStar2GroupList,
            boolean isTokenComma
    ) {
        this.exprlistStar1Group = exprlistStar1Group;
        this.exprlistStar2GroupList = exprlistStar2GroupList;
        this.isTokenComma = isTokenComma;
    }
}
