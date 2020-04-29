package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// funcdef: ['async'] 'def' ['varargslist'] (':' 'expr' | 'block_suite')
public final class Funcdef extends ConjunctionRule {
    private final boolean isTokenAsync;
    private final boolean isTokenDef;
    private final Varargslist varargslist;
    private final Funcdef4Group funcdef4Group;

    public Funcdef(
            boolean isTokenAsync,
            boolean isTokenDef,
            Varargslist varargslist,
            Funcdef4Group funcdef4Group
    ) {
        this.isTokenAsync = isTokenAsync;
        this.isTokenDef = isTokenDef;
        this.varargslist = varargslist;
        this.funcdef4Group = funcdef4Group;
    }

    public boolean getIsTokenAsync() {
        return isTokenAsync;
    }

    public boolean getIsTokenDef() {
        return isTokenDef;
    }

    public Optional<Varargslist> getVarargslist() {
        return Optional.ofNullable(varargslist);
    }

    public Funcdef4Group getFuncdef4Group() {
        return funcdef4Group;
    }

    // ':' 'expr' | 'block_suite'
    public static final class Funcdef4Group extends DisjunctionRule {
        private final Funcdef4Group1 funcdef4Group1;
        private final BlockSuite blockSuite;

        public Funcdef4Group(
                Funcdef4Group1 funcdef4Group1,
                BlockSuite blockSuite
        ) {
            this.funcdef4Group1 = funcdef4Group1;
            this.blockSuite = blockSuite;
        }

        public Funcdef4Group1 getFuncdef4Group1() {
            return funcdef4Group1;
        }

        public BlockSuite getBlockSuite() {
            return blockSuite;
        }
    }

    // ':' 'expr'
    public static final class Funcdef4Group1 extends ConjunctionRule {
        private final boolean isTokenColon;
        private final Expr expr;

        public Funcdef4Group1(
                boolean isTokenColon,
                Expr expr
        ) {
            this.isTokenColon = isTokenColon;
            this.expr = expr;
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}
