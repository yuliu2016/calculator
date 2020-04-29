package org.fugalang.core.pgen;

import java.util.List;

// (',' ('namedexpr_expr' | 'star_expr'))* [',']
public class ExprlistComp2Group2 {
    public final List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList;
    public final boolean isTokenComma;

    public ExprlistComp2Group2(
            List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList,
            boolean isTokenComma
    ) {
        this.exprlistComp2Group21GroupList = exprlistComp2Group21GroupList;
        this.isTokenComma = isTokenComma;
    }
}
