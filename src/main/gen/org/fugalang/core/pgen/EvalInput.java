package org.fugalang.core.pgen;

import java.util.List;

// eval_input: 'exprlist' 'NEWLINE'* 'ENDMARKER'
public class EvalInput {
    public final Exprlist exprlist;
    public final List<Object> newlineList;
    public final Object endmarker;

    public EvalInput(
            Exprlist exprlist,
            List<Object> newlineList,
            Object endmarker
    ) {
        this.exprlist = exprlist;
        this.newlineList = newlineList;
        this.endmarker = endmarker;
    }
}
