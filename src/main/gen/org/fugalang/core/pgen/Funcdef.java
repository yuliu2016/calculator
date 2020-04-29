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
}
