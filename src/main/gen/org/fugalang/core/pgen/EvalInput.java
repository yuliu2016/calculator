package org.fugalang.core.pgen;

import java.util.List;

// eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
public class EvalInput {
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

    public Exprlist getExprlist() {
        return exprlist;
    }

    public List<Object> getNewlineList() {
        return newlineList;
    }

    public Object getEndmarker() {
        return endmarker;
    }
}
