package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// funcdef: ['async'] 'def' ['varargslist'] (':' 'expr' | 'block_suite')
public final class Funcdef extends ConjunctionRule {
    public static final String RULE_NAME = "funcdef";

    private final boolean isTokenAsync;
    private final boolean isTokenDef;
    private final Varargslist varargslist;
    private final Funcdef4 funcdef4;

    public Funcdef(
            boolean isTokenAsync,
            boolean isTokenDef,
            Varargslist varargslist,
            Funcdef4 funcdef4
    ) {
        this.isTokenAsync = isTokenAsync;
        this.isTokenDef = isTokenDef;
        this.varargslist = varargslist;
        this.funcdef4 = funcdef4;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenAsync", isTokenAsync);
        addRequired("isTokenDef", isTokenDef);
        addOptional("varargslist", varargslist);
        addRequired("funcdef4", funcdef4);
    }

    public boolean isTokenAsync() {
        return isTokenAsync;
    }

    public boolean isTokenDef() {
        return isTokenDef;
    }

    public Optional<Varargslist> varargslist() {
        return Optional.ofNullable(varargslist);
    }

    public Funcdef4 funcdef4() {
        return funcdef4;
    }

    // ':' 'expr' | 'block_suite'
    public static final class Funcdef4 extends DisjunctionRule {
        public static final String RULE_NAME = "funcdef:4";

        private final Funcdef41 funcdef41;
        private final BlockSuite blockSuite;

        public Funcdef4(
                Funcdef41 funcdef41,
                BlockSuite blockSuite
        ) {
            this.funcdef41 = funcdef41;
            this.blockSuite = blockSuite;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("funcdef41", funcdef41);
            addChoice("blockSuite", blockSuite);
        }

        public Funcdef41 funcdef41() {
            return funcdef41;
        }

        public BlockSuite blockSuite() {
            return blockSuite;
        }
    }

    // ':' 'expr'
    public static final class Funcdef41 extends ConjunctionRule {
        public static final String RULE_NAME = "funcdef:4:1";

        private final boolean isTokenColon;
        private final Expr expr;

        public Funcdef41(
                boolean isTokenColon,
                Expr expr
        ) {
            this.isTokenColon = isTokenColon;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenColon", isTokenColon);
            addRequired("expr", expr);
        }

        public boolean isTokenColon() {
            return isTokenColon;
        }

        public Expr expr() {
            return expr;
        }
    }
}
