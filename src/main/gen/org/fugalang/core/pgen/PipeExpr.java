package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// pipe_expr: 'atom_expr' ('->' 'atom_expr')*
public final class PipeExpr extends ConjunctionRule {
    public static final String RULE_NAME = "pipe_expr";

    private final AtomExpr atomExpr;
    private final List<PipeExpr2> pipeExpr2List;

    public PipeExpr(
            AtomExpr atomExpr,
            List<PipeExpr2> pipeExpr2List
    ) {
        this.atomExpr = atomExpr;
        this.pipeExpr2List = pipeExpr2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("atomExpr", atomExpr);
        addRequired("pipeExpr2List", pipeExpr2List);
    }

    public AtomExpr atomExpr() {
        return atomExpr;
    }

    public List<PipeExpr2> pipeExpr2List() {
        return pipeExpr2List;
    }

    // '->' 'atom_expr'
    public static final class PipeExpr2 extends ConjunctionRule {
        public static final String RULE_NAME = "pipe_expr:2";

        private final boolean isTokenPipe;
        private final AtomExpr atomExpr;

        public PipeExpr2(
                boolean isTokenPipe,
                AtomExpr atomExpr
        ) {
            this.isTokenPipe = isTokenPipe;
            this.atomExpr = atomExpr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenPipe", isTokenPipe);
            addRequired("atomExpr", atomExpr);
        }

        public boolean isTokenPipe() {
            return isTokenPipe;
        }

        public AtomExpr atomExpr() {
            return atomExpr;
        }
    }
}
