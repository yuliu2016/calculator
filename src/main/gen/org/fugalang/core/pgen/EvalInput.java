package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
public final class EvalInput extends ConjunctionRule {
    private final Exprlist exprlist;
    private final List<Object> newlineList;
    private final Object endmarker;

    public EvalInput(
            Exprlist exprlist,
            List<Object> newlineList,
            Object endmarker
    ) {
        this.exprlist = exprlist;
        this.newlineList = newlineList;
        this.endmarker = endmarker;
    }

    @Override
    protected void buildRule() {
        addRequired("exprlist", exprlist);
        addRequired("newlineList", newlineList);
        addRequired("endmarker", endmarker);
    }

    public Exprlist exprlist() {
        return exprlist;
    }

    public List<Object> newlineList() {
        return newlineList;
    }

    public Object endmarker() {
        return endmarker;
    }
}
