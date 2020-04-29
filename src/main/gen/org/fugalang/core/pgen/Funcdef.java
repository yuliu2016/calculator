package org.fugalang.core.pgen;

// funcdef: ['async'] 'def' ['varargslist'] (':' 'expr' | 'block_suite')
public class Funcdef {
    public final boolean isTokenAsync;
    public final boolean isTokenDef;
    public final Varargslist varargslist;
    public final Funcdef4Group funcdef4Group;

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

    // ':' 'expr' | 'block_suite'
    public static class Funcdef4Group {
        public final Funcdef4Group1 funcdef4Group1;
        public final BlockSuite blockSuite;

        public Funcdef4Group(
                Funcdef4Group1 funcdef4Group1,
                BlockSuite blockSuite
        ) {
            this.funcdef4Group1 = funcdef4Group1;
            this.blockSuite = blockSuite;
        }
    }

    // ':' 'expr'
    public static class Funcdef4Group1 {
        public final boolean isTokenColon;
        public final Expr expr;

        public Funcdef4Group1(
                boolean isTokenColon,
                Expr expr
        ) {
            this.isTokenColon = isTokenColon;
            this.expr = expr;
        }
    }
}
